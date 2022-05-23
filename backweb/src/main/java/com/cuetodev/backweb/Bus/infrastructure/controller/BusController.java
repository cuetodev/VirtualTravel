package com.cuetodev.backweb.Bus.infrastructure.controller;

import com.cuetodev.backweb.Bus.application.port.BusPort;
import com.cuetodev.backweb.Bus.infrastructure.controller.dto.output.BookingsByBusOutputDTO;
import com.cuetodev.backweb.Bus.infrastructure.controller.dto.output.BusAvailabilityOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v0/bus")
public class BusController {

    @Autowired
    private BusPort busPort;

    @GetMapping("/available/{destinationCity}")
    public ResponseEntity<List<BusAvailabilityOutputDTO>> getAvailableBuses(
            @PathVariable String destinationCity,
            @RequestParam String lowerDate,
            @RequestParam(required = false, defaultValue = "noDate") String upperDate,
            @RequestParam(required = false, defaultValue = "-1") Float lowerHour,
            @RequestParam(required = false, defaultValue = "-1") Float upperHour) {

        HashMap<String, Object> conditions = new HashMap<>();
        conditions.put("city", destinationCity);
        conditions.put("lowerDate", lowerDate);
        conditions.put("upperDate", upperDate);
        conditions.put("lowerHour", lowerHour);
        conditions.put("upperHour", upperHour);

        List<BusAvailabilityOutputDTO> busAvailabilityOutputDTOS;
        busAvailabilityOutputDTOS = busPort.getAvailableBuses(conditions);

        return new ResponseEntity<>(busAvailabilityOutputDTOS, HttpStatus.OK);
    }

    /*
     *  I search the specific bus by the given data and return the bus booking list
     *  rather than search in the whole book table.
     *  I do this cause of efficiency so bus table is smaller than book one
     */
    @GetMapping("/book/{destinationCity}")
    public ResponseEntity<List<BookingsByBusOutputDTO>> getBookingsByBus(
            @PathVariable String destinationCity,
            @RequestParam String lowerDate,
            @RequestParam(required = false, defaultValue = "noDate") String upperDate,
            @RequestParam(required = false, defaultValue = "-1") Float lowerHour,
            @RequestParam(required = false, defaultValue = "-1") Float upperHour) {

        HashMap<String, Object> conditions = new HashMap<>();
        conditions.put("city", destinationCity);
        conditions.put("lowerDate", lowerDate);
        conditions.put("upperDate", upperDate);
        conditions.put("lowerHour", lowerHour);
        conditions.put("upperHour", upperHour);

        List<BookingsByBusOutputDTO> bookingsByBusOutputDTOS;
        bookingsByBusOutputDTOS = busPort.getBookingsByBus(conditions);

        return new ResponseEntity<>(bookingsByBusOutputDTOS, HttpStatus.OK);
    }

}
