package ru.practicum.common.validation;

import ru.practicum.common.validation.annotation.FutureDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static ru.practicum.util.Constants.TIME_PATTERN;

public class FutureDateValidator implements ConstraintValidator<FutureDate, String> {
    @Override
    public void initialize(FutureDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(s)) return true;
        LocalDateTime eventDate = LocalDateTime.parse(s, DateTimeFormatter.ofPattern(TIME_PATTERN));
        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(eventDate);
    }
}
