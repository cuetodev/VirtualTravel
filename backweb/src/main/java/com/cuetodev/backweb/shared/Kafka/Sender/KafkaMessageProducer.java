package com.cuetodev.backweb.shared.Kafka.Sender;


import com.cuetodev.backweb.Booking.domain.Booking;
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

    @Value(value = "${message.topic.name:booking}")
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
                System.out.println("** OBJETO ENVIADO **");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Error sending the object");
            }
        });
    }
}
