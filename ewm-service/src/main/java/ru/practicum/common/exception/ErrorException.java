package ru.practicum.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import ru.practicum.common.exception.factory.ApiErrorFactory;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorException {
    private final ApiErrorFactory apiErrorFactory;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleException(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        log.warn("");
        return ResponseEntity
                .status(status)
                .body(apiErrorFactory.create(ex, status, "Incorrectly made request."));
    }

    @ExceptionHandler(MyException.class)
    protected ResponseEntity<?> handleException(MyException ex) {
        log.warn("");
        return ResponseEntity
                .status(ex.getStatus())
                .body(apiErrorFactory.create(ex, ex.getStatus(), ex.getReason()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<?> handleException(ConstraintViolationException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        log.warn("");
        return ResponseEntity
                .status(status)
                .body(apiErrorFactory.create(ex, status, "Integrity constraint has been violated."));
    }

    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<?> handleException(ConflictException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        log.warn("");
        return ResponseEntity
                .status(status)
                .body(apiErrorFactory.create(ex, status, ""));
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<?> handleException(NotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        log.warn("");
        return ResponseEntity
                .status(status)
                .body(apiErrorFactory.create(ex, status, "The required object was not found."));
    }
}