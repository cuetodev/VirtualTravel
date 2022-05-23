package com.cuetodev.backempresa.Booking.infrastructure.controller.dto.output;

import com.cuetodev.backempresa.Booking.domain.Booking;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
public class BookingOutPutDTO {
    private Integer id;
    private String city;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String date;
    private Float hour;
    private String status;

    public BookingOutPutDTO(Booking booking) throws ParseException {
        if (booking == null) return;
        setId(booking.getId());
        setCity(booking.getCity());
        setName(booking.getName());
        setSurname(booking.getSurname());
        setPhone(booking.getPhone());
        setEmail(booking.getEmail());
        setDate(new SimpleDateFormat("yyyy-MM-dd").format(booking.getDate()));
        setHour(booking.getHour());
        setStatus(booking.getStatus());
    }

}
