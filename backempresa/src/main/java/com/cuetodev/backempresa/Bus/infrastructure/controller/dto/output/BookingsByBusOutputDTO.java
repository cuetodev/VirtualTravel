package com.cuetodev.backempresa.Bus.infrastructure.controller.dto.output;

import com.cuetodev.backempresa.Booking.domain.Booking;
import com.cuetodev.backempresa.Bus.domain.Bus;
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
