package com.cuetodev.backempresa.Booking.application;

import com.cuetodev.backempresa.Booking.application.port.BookingPort;
import com.cuetodev.backempresa.Booking.domain.Booking;
import com.cuetodev.backempresa.Booking.infrastructure.controller.dto.output.BookingOutPutDTO;
import com.cuetodev.backempresa.Booking.infrastructure.repository.port.BookingRepositoryPort;
import com.cuetodev.backempresa.Bus.domain.Bus;
import com.cuetodev.backempresa.Bus.infrastructure.repository.port.BusRepositoryPort;
import com.cuetodev.backempresa.Mail.application.port.MailPort;
import com.cuetodev.backempresa.Mail.domain.Mail;
import com.cuetodev.backempresa.shared.ErrorHandling.ErrorOutPutDTO;
import com.cuetodev.backempresa.shared.Kafka.Sender.KafkaMessageProducer;
import com.cuetodev.backempresa.shared.Mail.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BookingUseCase implements BookingPort {

    @Autowired
    BookingRepositoryPort bookingRepositoryPort;

    @Autowired
    BusRepositoryPort busRepositoryPort;

    @Autowired
    KafkaMessageProducer kafkaMessageProducer;

    @Autowired
    MailPort mailPort;

    // I'm using condition variable when I want to insert
    // a booking directly in backempresa -> condition will be: "direct"
    @Override
    public Booking postBooking(Booking bookingReceived, String condition) {
        Bus bus = busRepositoryPort.findByCityAndDateAndHour(bookingReceived.getCity(), bookingReceived.getDate(), bookingReceived.getHour());
        Booking bookingSaved = null;

        if (bus != null) { // If the bus is already created in BD...
            int occupiedSeats = bus.getOccupiedSeats();
            if (occupiedSeats < 40 && bus.isActive()) {
                bookingReceived.setStatus("Accepted");
                bookingSaved = insertAcceptedBooking(bookingReceived, bus, occupiedSeats, condition);

                //Sending confirmation email
                SendMail.sendMail(
                        bookingSaved.getEmail(),
                        "virtual.travel.david.cueto@gmail.com",
                        "VirtualTravel - Booking confirmation",
                        confirmationMessage(bookingSaved),
                        "html"
                );
            } else {
                bookingReceived.setStatus("Cancelled");
                if (occupiedSeats >= 40) {
                    bookingSaved = creatingMailAndBooking(bookingReceived, "Virtual Travel - Booking Cancelled (Bus Full)", condition);

                    //Sending cancelled email because bus is full
                    SendMail.sendMail(
                            bookingSaved.getEmail(),
                            "virtual.travel.david.cueto@gmail.com",
                            "VirtualTravel - Booking cancelled (Bus full)",
                            cancelledMessage("full", bookingSaved),
                            "html"
                    );
                } else if (!bus.isActive()) {
                    bookingSaved = creatingMailAndBooking(bookingReceived, "Virtual Travel - Booking Cancelled (Bus inactive)", condition);

                    //Sending cancelled email because bus is inactive
                    SendMail.sendMail(
                            bookingSaved.getEmail(),
                            "virtual.travel.david.cueto@gmail.com",
                            "VirtualTravel - Booking cancelled (Bus inactive)",
                            cancelledMessage("inactive", bookingSaved),
                            "html"
                    );
                }
                // If I create a booking directly in empresa I send to backweb the updated bus
                if (condition.equals("direct")) kafkaMessageProducer.sendBooking("bookingReceive", bookingSaved);

            }
        } else { // If there is no bus created in BD...
            bookingReceived.setStatus("Accepted");
            bookingSaved = insertAcceptedBookingAndCreateABus(bookingReceived, condition, kafkaMessageProducer);

            //Sending confirmation email
            SendMail.sendMail(
                    bookingSaved.getEmail(),
                    "virtual.travel.david.cueto@gmail.com",
                    "VirtualTravel - Booking confirmation",
                    confirmationMessage(bookingSaved),
                    "html"
            );
        }
        return bookingSaved;
    }

    @Override
    public List<BookingOutPutDTO> getBookingsByConditions(HashMap<String, Object> conditions) {
        List<Booking> bookings = bookingRepositoryPort.getBookingsByConditions(conditions);
        List<BookingOutPutDTO> bookingOutPutDTOList = new ArrayList<>();
        bookings.forEach(booking -> {
            try {
                bookingOutPutDTOList.add(new BookingOutPutDTO(booking));
            } catch (ParseException e) {
                throw new ErrorOutPutDTO(400, "Parse error", "Fatal");
            }
        });
        return bookingOutPutDTOList;
    }

    private Booking creatingMailAndBooking(Booking bookingReceived, String subject, String condition) {
        Booking bookingSaved;
        // Creating the mail
        Mail newMail = new Mail(null, "virtual.travel.david.cueto@gmail.com", bookingReceived.getEmail(), subject);
        Mail finalMail = mailPort.postMail(newMail);

        // This variable is used to add the first mail to the booking cause it needs a HashSet
        Set<Mail> mailSet = new HashSet<>();
        mailSet.add(finalMail);

        bookingReceived.setMailSet(mailSet);

        bookingSaved = bookingRepositoryPort.saveBooking(bookingReceived);
        return bookingSaved;
    }

    private Booking insertAcceptedBookingAndCreateABus(Booking bookingReceived, String condition, KafkaMessageProducer kafkaMessageProducer) {
        Booking bookingSaved;

        // Creating the mail
        Mail newMail = new Mail(null, "virtual.travel.david.cueto@gmail.com", bookingReceived.getEmail(), "Virtual Travel - Booking Accepted");
        mailPort.postMail(newMail);

        // This variable is used to add the first mail to the booking cause it needs a HashSet
        Set<Mail> mailSet = new HashSet<>();
        mailSet.add(newMail);
        bookingReceived.setMailSet(mailSet);

        Set<Booking> bookings = new HashSet<>();
        bookings.add(bookingReceived);
        bookingSaved = bookingRepositoryPort.saveBooking(bookingReceived);

        // Here I create the bus (save)
        Bus newBus = new Bus(null, true, bookingReceived.getCity(), bookingReceived.getHour(), bookingReceived.getDate(), bookings);
        Bus finalBus = busRepositoryPort.createUpdateBus(newBus);
        // If I create a booking directly in empresa I send it to backweb
        if (condition.equals("direct")) kafkaMessageProducer.sendBooking("bookingReceived", bookingSaved);

        return bookingSaved;
    }

    private Booking insertAcceptedBooking(Booking bookingReceived, Bus bus, int occupiedSeats, String condition) {
        Booking bookingSaved;

        // Creating the mail
        bookingSaved = creatingMailAndBooking(bookingReceived, "Virtual Travel - Booking Accepted", condition);

        bus.setOccupiedSeats(occupiedSeats + 1);
        bus.getBookingList().add(bookingReceived);
        // Then I update the bus with a new booking (save)
        busRepositoryPort.createUpdateBus(bus);

        if (condition.equals("direct")) kafkaMessageProducer.sendBooking("bookingReceived", bookingSaved);

        return bookingSaved;
    }

    private String confirmationMessage(Booking booking) {
        return "<h1>Virtual Travel - Booking Accepted<h1>" +
                "<h3>Destination: " + booking.getCity() + "</h3>" +
                "<h3>Date: " + formatDate(booking.getDate()) + "</h3>" +
                "<h3>Hour: " + booking.getHour() + "</h3>" +
                "<h3>Identifier: " + booking.getId() + "</h3>";
    }

    // Condition can be "full" or "inactive" so I personalize my error with 1 function
    private String cancelledMessage(String condition, Booking booking) {
        return "<h1>Virtual Travel - Booking Cancelled<h1>" +
                "<h3>Sorry but your booking with destination: " + booking.getCity() +
                "and date: " + formatDate(booking.getDate()) + " at " + booking.getHour() +
                " has been cancelled due to" +
                (condition.equals("full")
                        ? " the bus is full"
                        : " an unexpected problem with the bus")
                + " </h3>";
    }

    private String formatDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
