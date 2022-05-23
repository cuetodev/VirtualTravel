package com.cuetodev.backweb.Booking.application;

import com.cuetodev.backweb.Booking.application.port.BookingPort;
import com.cuetodev.backweb.Booking.domain.Booking;
import com.cuetodev.backweb.Booking.infrastructure.controller.dto.output.BookingOutPutDTO;
import com.cuetodev.backweb.Booking.infrastructure.repository.port.BookingRepositoryPort;
import com.cuetodev.backweb.Bus.domain.Bus;
import com.cuetodev.backweb.Bus.infrastructure.repository.port.BusRepositoryPort;
import com.cuetodev.backweb.shared.ErrorHandling.ErrorOutPutDTO;
import com.cuetodev.backweb.shared.Kafka.Sender.KafkaMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
public class BookingUseCase implements BookingPort {
    @Autowired
    BookingRepositoryPort bookingRepositoryPort;

    @Autowired
    BusRepositoryPort busRepositoryPort;

    @Autowired
    KafkaMessageProducer kafkaMessageProducer;

    @Override
    public Booking postBooking(Booking bookingReceived, String condition) {
        Bus bus = busRepositoryPort.findByCityAndDateAndHour(bookingReceived.getCity(), bookingReceived.getDate(), bookingReceived.getHour());
        Booking bookingSaved = null;

        if (bus != null) { // If the bus is already created in BD...
            int occupiedSeats = bus.getOccupiedSeats();
            if (occupiedSeats < 40 && bus.isActive()) {
                bookingReceived.setStatus("Accepted");
                bookingSaved = insertAcceptedBooking(bookingReceived, bus, occupiedSeats, condition);
            } else {
                bookingReceived.setStatus("Cancelled");
                bookingSaved = bookingRepositoryPort.saveBooking(bookingReceived);
            }
        } else { // If there is no bus created in BD...
            bookingReceived.setStatus("Accepted");
            bookingSaved = insertAcceptedBookingAndCreateABus(bookingReceived, condition);
        }
        if (!condition.equals("noSend")) kafkaMessageProducer.sendBooking("booking", bookingSaved);
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

    @Override
    public Booking findById(Integer id) {
        return bookingRepositoryPort.findById(id);
    }

    private Booking insertAcceptedBookingAndCreateABus(Booking bookingReceived, String condition) {
        Set<Booking> bookings = new HashSet<>();
        bookings.add(bookingReceived);
        Booking bookingSaved = bookingRepositoryPort.saveBooking(bookingReceived);

        // Here I create the bus (save)
        Bus newBus = new Bus(null, true, bookingReceived.getCity(), bookingReceived.getHour(), bookingReceived.getDate(), bookings);
        if (!condition.equals("send")) newBus.setOccupiedSeats(1);
        busRepositoryPort.createUpdateBus(newBus);
        return bookingSaved;
    }

    private Booking insertAcceptedBooking(Booking bookingReceived, Bus bus, int occupiedSeats, String condition) {
        Booking bookingSaved;
        if (!condition.equals("send")) bus.setOccupiedSeats(occupiedSeats + 1);
        bus.getBookingList().add(bookingReceived);
        bookingReceived.setStatus("Accepted");

        bookingSaved = bookingRepositoryPort.saveBooking(bookingReceived);
        // Then I update the bus with a new booking (save)
        busRepositoryPort.createUpdateBus(bus);

        return bookingSaved;
    }
}
