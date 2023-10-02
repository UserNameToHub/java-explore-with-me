package ru.practicum.common.validation;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.common.validation.annotation.CreationDateConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.practicum.util.Constants.TIME_PATTERN;

@Slf4j
public class CreationDateConstraintValidator implements ConstraintValidator<CreationDateConstraint, String> {
    private String hour;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime eventDateTime = LocalDateTime.parse(s, DateTimeFormatter.ofPattern(TIME_PATTERN));
        LocalDateTime checkDate = LocalDateTime.now().plusHours(Long.parseLong(hour));
        log.debug("Валидация даты создания события. Дата создания события - {}", eventDateTime);

        return checkDate.isBefore(eventDateTime);
    }

    @Override
    public void initialize(CreationDateConstraint constraintAnnotation) {
        log.debug("Инициализация поля hour значением {}", constraintAnnotation.hour());
        hour = constraintAnnotation.hour();
    }
}
