package ru.practicum.common.validation.annotation;

import ru.practicum.common.validation.HourAfterValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HourAfterValidator.class)
public @interface CompareDateConstraint {
    String date();

    String message() default "Дата начала больше даты окончания периода.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
