package com.cuetodev.backempresa.Mail.infrastructure.controller.dto.output;

import com.cuetodev.backempresa.Booking.domain.Booking;
import com.cuetodev.backempresa.Mail.domain.Mail;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
public class MailOutputDTO {
    private String city;
    private String receiver;
    private String bookingDate;
    private Float bookingHour;
    private String subject;

    public MailOutputDTO(Mail mail, Booking booking) {
        this.city = booking.getCity();
        this.receiver = mail.getReceiver();
        this.bookingDate = new SimpleDateFormat("yyyy-MM-dd").format(booking.getDate());
        this.bookingHour = booking.getHour();
        this.subject = mail.getSubject();
    }
}
