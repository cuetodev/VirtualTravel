package com.cuetodev.backweb.Bus.infrastructure.controller.dto.output;

import com.cuetodev.backweb.Bus.domain.Bus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
public class BusAvailabilityOutputDTO {
    private String city;
    private String departureDate;
    private Float departureHour;
    private Integer availableSeats;

    public BusAvailabilityOutputDTO(Bus bus) {
        setCity(bus.getCity());
        setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").format(bus.getDate()));
        setDepartureHour(bus.getHour());
        setAvailableSeats(40 - bus.getOccupiedSeats());
    }
}
