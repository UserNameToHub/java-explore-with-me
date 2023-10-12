package ru.practicum.common.exception.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError implements ApplicationError {
    private String status;
    private String reason;
    private String message;
    private String timestamp;
}
