package com.cuetodev.backweb.shared.Validator.DatePatternConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DatePatternCheck implements
        ConstraintValidator<DatePatternCheckConstraint, String> {

    @Override
    public void initialize(DatePatternCheckConstraint date) {
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        return date != null && date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

}
