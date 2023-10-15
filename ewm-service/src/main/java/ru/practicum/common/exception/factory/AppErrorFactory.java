package ru.practicum.common.exception.factory;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.practicum.common.exception.model.ApplicationError;

public abstract class AppErrorFactory {
    public <T extends Exception> ApplicationError create(T rex, HttpStatus responseCode, String reason) {
        ApplicationError appError = createAppError(rex, responseCode, reason);
        return appError;
    }

    protected abstract <T extends Exception>  ApplicationError createAppError(T rex, HttpStatus responseCode, String reason);
}
