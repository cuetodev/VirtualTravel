package com.cuetodev.backweb.shared.Validator.CityCheckConstraint;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CityCheck.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CityCheckConstraint {
    String message() default "Invalid city";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
