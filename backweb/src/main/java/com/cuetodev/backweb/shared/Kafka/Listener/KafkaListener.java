package com.cuetodev.backweb.shared.Kafka.Listener;

import com.cuetodev.backweb.Booking.application.port.BookingPort;
import com.cuetodev.backweb.Booking.domain.Booking;
import com.cuetodev.backweb.Bus.application.port.BusPort;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaListener {

    @Autowired
    BookingPort bookingPort;

    @Autowired
    BusPort busPort;

    @org.springframework.kafka.annotation.KafkaListener(topics = "${message.topic.nameReceived:bookingReceived}", groupId = "${message.group.nameReceived:bookingGroupReceived}")
    public void bookingReceivedFromBackEmpresa(String bookingString) {
        System.out.println("** BOOKING RECIBIDO **");
        Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy HH:mm:ss").create();
        Booking bookingReceivedFromBackEmpresa = gson.fromJson(bookingString, Booking.class);
        Booking bookingInBD = bookingPort.findById(bookingReceivedFromBackEmpresa.getId());

        if (bookingInBD == null) bookingPort.postBooking(bookingReceivedFromBackEmpresa, "noSend");
        else {
            bookingInBD.setStatus(bookingReceivedFromBackEmpresa.getStatus());
            bookingPort.postBooking(bookingInBD, "noSend");
        }
    }
}
