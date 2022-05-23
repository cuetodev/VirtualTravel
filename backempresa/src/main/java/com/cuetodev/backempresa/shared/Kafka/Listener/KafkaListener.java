package com.cuetodev.backempresa.shared.Kafka.Listener;

import com.cuetodev.backempresa.Booking.application.port.BookingPort;
import com.cuetodev.backempresa.Booking.domain.Booking;
import com.cuetodev.backempresa.shared.Kafka.Sender.KafkaMessageProducer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaListener {

    @Autowired
    BookingPort bookingPort;

    @Autowired
    KafkaMessageProducer kafkaMessageProducer;

    @org.springframework.kafka.annotation.KafkaListener(topics = "${message.topic.name:booking}", groupId = "${message.group.name:bookingGroup}")
    public void bookingReceivedFromBackWeb(String bookingString) {
        System.out.println("** BOOKING RECIBIDO **");
        Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy HH:mm:ss").create();
        Booking bookingReceivedFromBackWeb = gson.fromJson(bookingString, Booking.class);
        bookingReceivedFromBackWeb.setMailSet(null);

        Booking finalBooking = bookingPort.postBooking(bookingReceivedFromBackWeb, "noOne");
        kafkaMessageProducer.sendBooking("bookingReceived", finalBooking);
    }
}
