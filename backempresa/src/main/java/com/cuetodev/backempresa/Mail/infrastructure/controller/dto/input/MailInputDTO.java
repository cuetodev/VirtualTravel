package com.cuetodev.backempresa.Mail.infrastructure.controller.dto.input;

import com.cuetodev.backempresa.Mail.domain.Mail;
import com.cuetodev.backempresa.shared.Validator.CityCheckConstraint.CityCheckConstraint;
import com.cuetodev.backempresa.shared.Validator.DatePatternConstraint.DatePatternCheckConstraint;
import com.cuetodev.backempresa.shared.Validator.HourCheckConstraint.HourCheckConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class MailInputDTO {
    @NotBlank(message = "City can't be empty")
    @CityCheckConstraint
    private String city;

    @NotBlank(message = "Email can't be empty")
    private String email;

    @NotBlank(message = "Date can't be empty")
    @DatePatternCheckConstraint
    private String date;

    @NotNull(message = "Hour can't be empty")
    @HourCheckConstraint
    private Float hour;

    public Mail convertToEntity() {
        return new Mail();
    }
}
