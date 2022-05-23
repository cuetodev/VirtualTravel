package com.cuetodev.backweb.Booking.infrastructure.controller.dto.input;

import com.cuetodev.backweb.Booking.domain.Booking;
import com.cuetodev.backweb.shared.Validator.CityCheckConstraint.CityCheckConstraint;
import com.cuetodev.backweb.shared.Validator.DatePatternConstraint.DatePatternCheckConstraint;
import com.cuetodev.backweb.shared.Validator.HourCheckConstraint.HourCheckConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingInputDTO {

    @NotBlank(message = "City can't be empty")
    @CityCheckConstraint
    private String city;

    @NotBlank(message = "Name can't be empty")
    private String name;

    @NotBlank(message = "Surname can't be empty")
    private String surname;

    @NotBlank(message = "Phone can't be empty")
    private String phone;

    @NotBlank(message = "Email can't be empty")
    private String email;

    @NotBlank(message = "Date can't be empty")
    @DatePatternCheckConstraint
    private String date;

    @NotNull(message = "Hour can't be empty")
    @HourCheckConstraint
    private Float hour;

    public Booking convertInputDtoToEntity() throws ParseException {
        // I set date after because I validate it being a String
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(this.date);
        return new Booking(null, city, name, surname, phone, email, date, hour, null);
    }
}
