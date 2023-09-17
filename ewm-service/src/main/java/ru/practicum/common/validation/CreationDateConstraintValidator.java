package ru.practicum.common.validation;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.common.validation.annotation.CreationDateConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

@Slf4j
public class CreationDateConstraintValidator implements ConstraintValidator<CreationDateConstraint, LocalDateTime> {
    private String hour;

    @Override
    public boolean isValid(LocalDateTime currentDate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime creationDate = currentDate.plusHours(Long.parseLong(hour));
        log.debug("Валидация даты создания события. Текущая дата - {}, Дата создания события - {}",
                currentDate, creationDate);
        return currentDate.isBefore(creationDate);
    }

    @Override
    public void initialize(CreationDateConstraint constraintAnnotation) {
        log.debug("Инициализация поля hour значением {}", constraintAnnotation.hour());
        hour = constraintAnnotation.hour();
    }
}
