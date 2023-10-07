package ru.practicum.common.validation.annotation;


import ru.practicum.common.validation.FutureDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureDateValidator.class)
public @interface FutureDate {
    String message() default "Должно содержать дату, которая еще не наступила.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
