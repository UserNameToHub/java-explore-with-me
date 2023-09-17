package ru.practicum.common.exception.model;

public interface ApplicationError {
    StackTraceElement[] getErrors();
    String getMessage();
    String getReason();
    String getStatus();
    String getTimestamp();
}
