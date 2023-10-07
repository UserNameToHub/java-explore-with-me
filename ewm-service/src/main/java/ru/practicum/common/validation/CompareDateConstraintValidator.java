package ru.practicum.common.validation;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.common.interfaces.CompareDate;
import ru.practicum.common.validation.annotation.CompareDateConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static ru.practicum.util.Constants.TIME_PATTERN;

@Slf4j
public class CompareDateConstraintValidator implements ConstraintValidator<CompareDateConstraint, CompareDate> {
    @Override
    public boolean isValid(CompareDate compareDate, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.isNull(compareDate.getRangeStart()) || Objects.isNull(compareDate.getRangeStart()) ?
                true : LocalDateTime.parse(compareDate.getRangeStart(), DateTimeFormatter.ofPattern(TIME_PATTERN))
                .isBefore(LocalDateTime.parse(compareDate.getRangeEnd(), DateTimeFormatter.ofPattern(TIME_PATTERN)));
    }
}
