package com.cuetodev.backempresa.shared.Validator.DatePatternConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

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
