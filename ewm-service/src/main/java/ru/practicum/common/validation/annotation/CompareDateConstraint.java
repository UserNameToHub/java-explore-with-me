package ru.practicum.common.validation.annotation;

import ru.practicum.common.validation.CreationDateConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CreationDateConstraintValidator.class)
public @interface CompareDateConstraint {
    String message() default "Дата начала больше даты окончания периода.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
