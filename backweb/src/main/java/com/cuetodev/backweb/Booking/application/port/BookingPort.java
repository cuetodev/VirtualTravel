package com.cuetodev.backweb.Booking.application.port;

import com.cuetodev.backweb.Booking.domain.Booking;
import com.cuetodev.backweb.Booking.infrastructure.controller.dto.output.BookingOutPutDTO;

import java.util.HashMap;
import java.util.List;

public interface BookingPort {
    public Booking postBooking(Booking bookingReceived, String condition);
    public List<BookingOutPutDTO> getBookingsByConditions(HashMap<String, Object> conditions);
    public Booking findById(Integer id);
}
