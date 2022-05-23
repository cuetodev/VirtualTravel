package com.cuetodev.backempresa.shared.Validator.CityCheckConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CityCheck implements
        ConstraintValidator<CityCheckConstraint, String> {

    @Override
    public void initialize(CityCheckConstraint city) {
    }

    @Override
    public boolean isValid(String city, ConstraintValidatorContext constraintValidatorContext) {
        return city != null
                && (city.equalsIgnoreCase("Valencia")
                || city.equalsIgnoreCase("Madrid")
                || city.equalsIgnoreCase("Barcelona")
                || city.equalsIgnoreCase("Bilbao"));
    }

}
