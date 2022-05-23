package com.cuetodev.backempresa.shared.Validator.HourCheckConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HourCheck implements
        ConstraintValidator<HourCheckConstraint, Float> {

    @Override
    public void initialize(HourCheckConstraint hour) {
    }

    @Override
    public boolean isValid(Float hour, ConstraintValidatorContext constraintValidatorContext) {
        return hour != null && (hour == 8 || hour == 12 || hour == 16 || hour == 20);
    }

}
