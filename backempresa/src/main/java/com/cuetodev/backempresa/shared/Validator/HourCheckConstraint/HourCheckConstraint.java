package com.cuetodev.backempresa.shared.Validator.HourCheckConstraint;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HourCheck.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface HourCheckConstraint {
    String message() default "Invalid hour (8, 12, 16, 20)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
