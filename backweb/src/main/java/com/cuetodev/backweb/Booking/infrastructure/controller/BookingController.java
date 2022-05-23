package com.cuetodev.backweb.Booking.infrastructure.controller;

import com.cuetodev.backweb.Booking.application.port.BookingPort;
import com.cuetodev.backweb.Booking.domain.Booking;
import com.cuetodev.backweb.Booking.infrastructure.controller.dto.input.BookingInputDTO;
import com.cuetodev.backweb.Booking.infrastructure.controller.dto.output.BookingOutPutDTO;
import com.cuetodev.backweb.shared.ErrorHandling.ErrorOutPutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v0/booking")
public class BookingController {

    @Autowired
    private BookingPort bookingPort;

    @PostMapping
    public ResponseEntity<?> postBooking(@Valid @RequestBody BookingInputDTO bookingInputDTO) throws ParseException {
        Booking bookingReceived = null;
        Booking bookingBack = null;

        try {
            bookingReceived = bookingInputDTO.convertInputDtoToEntity();
            bookingBack = bookingPort.postBooking(bookingReceived, "send");
        } catch (Exception e) {
            return new ResponseEntity<ErrorOutPutDTO>(new ErrorOutPutDTO(400, "Invalidad booking", "Fatal"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<BookingOutPutDTO>(new BookingOutPutDTO(bookingBack), HttpStatus.OK);
    }

    @GetMapping("/{city}")
    public ResponseEntity<?> getBookingsByConditions(
            @PathVariable String city,
            @RequestParam String lowerDate,
            @RequestParam(required = false, defaultValue = "noDate") String upperDate,
            @RequestParam(required = false, defaultValue = "-1") Float lowerHour,
            @RequestParam(required = false, defaultValue = "-1") Float upperHour) {
        HashMap<String, Object> conditions = new HashMap<>();
        conditions.put("city", city);
        conditions.put("lowerDate", lowerDate);
        conditions.put("upperDate", upperDate);
        conditions.put("lowerHour", lowerHour);
        conditions.put("upperHour", upperHour);

        List<BookingOutPutDTO> bookingsByConditions = bookingPort.getBookingsByConditions(conditions);
        return new ResponseEntity<>(bookingsByConditions, HttpStatus.OK);
    }

}
