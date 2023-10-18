package ru.practicum.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.common.exception.factory.ApiErrorFactory;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleException(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        log.warn("Throw exception with code {}", ex.getMessage());
        return ResponseEntity
                .status(status)
                .body(new ApiErrorFactory().create(ex, status, "Incorrectly made request."));
    }

    @ExceptionHandler(MyException.class)
    protected ResponseEntity<?> handleException(MyException ex) {
        log.warn("Throw exception with code {} and message {}", ex.getStatus(), ex.getMessage());
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ApiErrorFactory().create(ex, ex.getStatus(), ex.getReason()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<?> handleException(DataIntegrityViolationException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        log.warn("Throw exception with code {}", ex.getMessage());
        return ResponseEntity
                .status(status)
                .body(new ApiErrorFactory().create(ex, status, "Integrity constraint has been violated."));
    }

    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<?> handleException(ConflictException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        log.warn("Throw exception with code {}", ex.getMessage());
        return ResponseEntity
                .status(status)
                .body(new ApiErrorFactory().create(ex, status, "Integrity constraint has been violated"));
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<?> handleException(NotFoundException ex) {
        log.warn("Throw exception with code {}", ex.getMessage());
        HttpStatus status = HttpStatus.NOT_FOUND;
        log.warn("");
        return ResponseEntity
                .status(status)
                .body(new ApiErrorFactory().create(ex, status, "The required object was not found."));
    }
}