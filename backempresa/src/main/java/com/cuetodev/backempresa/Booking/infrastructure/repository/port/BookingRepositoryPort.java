package com.cuetodev.backempresa.Booking.infrastructure.repository.port;

import com.cuetodev.backempresa.Booking.domain.Booking;

import java.util.HashMap;
import java.util.List;

public interface BookingRepositoryPort {
    public Booking saveBooking(Booking booking);
    public List<Booking> getBookingsByConditions(HashMap<String, Object> conditions);
    public Booking findById(Integer id);
}
