package ru.practicum.common.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class MyException extends RuntimeException {
    private final HttpStatus status;
    private String reason;

    public MyException(String message, HttpStatus status, String reason) {
        super(message);
        this.status = status;
        this.reason = reason;
    }
}
