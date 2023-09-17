package ru.practicum.common.exception.factory;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.practicum.common.exception.model.ApiError;

@Component
public class ApiErrorFactory extends AppErrorFactory {
    @Override
    protected <T extends Exception> ApiError createAppError(T rex, HttpStatus responseCode) {
        return ApiError.builder()
                .errors(rex.getStackTrace())
                .message(rex.getMessage())
                .reason(null)
                .status(responseCode.name())
                .timestamp(null)
                .build();
    }
}
