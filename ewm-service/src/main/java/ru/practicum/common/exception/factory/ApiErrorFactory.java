package ru.practicum.common.exception.factory;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.practicum.common.exception.model.ApiError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.practicum.util.Constants.TIME_PATTERN;

//@Component("ApiError")
public class ApiErrorFactory extends AppErrorFactory {
    @Override
    protected <T extends Exception> ApiError createAppError(T rex, HttpStatus responseCode, String reason) {
        return ApiError.builder()
                .message(rex.getMessage())
                .reason(reason)
                .status(responseCode.name())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIME_PATTERN)))
                .build();
    }
}
