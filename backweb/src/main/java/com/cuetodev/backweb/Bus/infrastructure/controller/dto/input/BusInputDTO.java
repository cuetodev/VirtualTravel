package com.cuetodev.backweb.Bus.infrastructure.controller.dto.input;

import com.cuetodev.backweb.Booking.domain.Booking;
import com.cuetodev.backweb.Bus.domain.Bus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class BusInputDTO {
    private Integer id;

    @NotNull(message = "Active can't be null")
    private boolean active;

    @NotBlank(message = "City can't be null")
    private String city;

    @NotBlank
    private int occupiedSeats;

    @NotNull
    private Float hour;

    @NotNull
    private Date date;

    private Set<Booking> bookingSet;

    public Bus convertToEntity() {
        return new Bus(null, active, city, hour, date, bookingSet);
    }
}
