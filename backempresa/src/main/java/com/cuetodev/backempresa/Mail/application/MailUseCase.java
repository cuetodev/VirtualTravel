package com.cuetodev.backempresa.Mail.application;

import com.cuetodev.backempresa.Booking.domain.Booking;
import com.cuetodev.backempresa.Booking.infrastructure.repository.port.BookingRepositoryPort;
import com.cuetodev.backempresa.Mail.application.port.MailPort;
import com.cuetodev.backempresa.Mail.domain.Mail;
import com.cuetodev.backempresa.Mail.infrastructure.controller.dto.input.MailInputDTO;
import com.cuetodev.backempresa.Mail.infrastructure.controller.dto.output.MailOutputDTO;
import com.cuetodev.backempresa.Mail.infrastructure.repository.port.MailRepositoryPort;
import com.cuetodev.backempresa.shared.Mail.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MailUseCase implements MailPort {

    @Autowired
    MailRepositoryPort mailRepositoryPort;

    @Autowired
    BookingRepositoryPort bookingRepositoryPort;


    @Override
    public Mail postMail(Mail mailReceived) {
        return mailRepositoryPort.postMail(mailReceived);
    }

    @Override
    public List<MailOutputDTO> getMailsByConditions(HashMap<String, Object> conditions) {
        List<Booking> bookings = bookingRepositoryPort.getBookingsByConditions(conditions);
        List<MailOutputDTO> mailList = new ArrayList<>();

        // Here Im accessing to the mail HashSet in each booking so I add every mail into one list to return the list
        bookings.forEach(booking -> {
            booking.getMailSet().forEach(mail -> {
                mailList.add(new MailOutputDTO(mail, booking));
            });
        });

        return mailList;
    }

    @Override
    public MailOutputDTO resend(MailInputDTO mail) {
        HashMap<String, Object> conditions = new HashMap<>();
        conditions.put("city", mail.getCity());
        conditions.put("equalDate", mail.getDate());
        conditions.put("equalHour", mail.getHour());
        conditions.put("email", mail.getEmail());

        List<Booking> bookings = bookingRepositoryPort.getBookingsByConditions(conditions);
        Booking bookingWanted = bookings.get(0);

        SendMail.sendMail(
                bookingWanted.getEmail(),
                "virtual.travel.david.cueto@gmail.com",
                "VirtualTravel - Resend Booking",
                reSendMessage(bookingWanted),
                "html"
        );

        Mail newMail = new Mail(null, "virtual.travel.david.cueto@gmail.com", bookingWanted.getEmail(), "Virtual Travel - Resend Booking");
        mailRepositoryPort.postMail(newMail);

        Set<Mail> mailSet = bookingWanted.getMailSet();
        mailSet.add(newMail);
        bookingWanted.setMailSet(mailSet);
        Booking bookingSaved = bookingRepositoryPort.saveBooking(bookingWanted);

        return new MailOutputDTO(newMail, bookingSaved);

    }

    private String reSendMessage(Booking booking) {
        if (booking.getStatus().equals("Accepted")) {
            return  "<h1>Virtual Travel - Booking Accepted<h1>" +
                    "<h3>Destination: " + booking.getCity() + "</h3>" +
                    "<h3>Date: " + formatDate(booking.getDate()) + "</h3>" +
                    "<h3>Hour: " + booking.getHour() + "</h3>" +
                    "<h3>Identifier: " + booking.getId() + "</h3>";
        } else {
            return "<h1>Virtual Travel - Booking Cancelled<h1>" +
                    "<h3>Sorry but your booking with destination: " + booking.getCity() +
                    "and date: " + formatDate(booking.getDate()) + " at " + booking.getHour() +
                    " has been cancelled </h3>";
        }
    }

    private String formatDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
