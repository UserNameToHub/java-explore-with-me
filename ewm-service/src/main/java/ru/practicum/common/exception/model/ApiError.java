package ru.practicum.common.exception.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError implements ApplicationError {
    private StackTraceElement[] errors;
    private String message;
    private String reason;
    private String status;
    private String timestamp;
}
