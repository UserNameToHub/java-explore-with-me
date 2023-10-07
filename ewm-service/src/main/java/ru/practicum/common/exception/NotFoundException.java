package ru.practicum.common.exception;

import org.springframework.lang.Nullable;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
