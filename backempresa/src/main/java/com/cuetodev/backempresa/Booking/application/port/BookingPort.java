package com.cuetodev.backempresa.Booking.application.port;

import com.cuetodev.backempresa.Booking.domain.Booking;
import com.cuetodev.backempresa.Booking.infrastructure.controller.dto.output.BookingOutPutDTO;

import java.util.HashMap;
import java.util.List;

public interface BookingPort {
    public Booking postBooking(Booking bookingReceived, String condition);
    public List<BookingOutPutDTO> getBookingsByConditions(HashMap<String, Object> conditions);
}
