package com.cuetodev.backweb.Bus.infrastructure.controller.dto.output;

import com.cuetodev.backweb.Booking.domain.Booking;
import com.cuetodev.backweb.Bus.domain.Bus;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class BookingsByBusOutputDTO {
    @NotNull
    private Set<Booking> bookingSet;

    public BookingsByBusOutputDTO (Bus bus) {
        setBookingSet(bus.getBookingList());
    }
}
