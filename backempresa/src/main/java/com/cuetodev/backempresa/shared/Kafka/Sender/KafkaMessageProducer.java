package com.cuetodev.backempresa.shared.Kafka.Sender;


import com.cuetodev.backempresa.Booking.domain.Booking;
import com.cuetodev.backempresa.Bus.domain.Bus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaMessageProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${message.topic.nameReceived:bookingReceived}")
    private String topicName;

    public void sendBooking(String topic, Booking booking) {

        // Object to String
        Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy HH:mm:ss").create();
        String bookingJson = gson.toJson(booking);

        if (topic == null || topic.trim().equals("")) topic = topicName;

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, bookingJson);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("** BOOKING ENVIADO **");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Error sending the object");
            }
        });
    }
}
