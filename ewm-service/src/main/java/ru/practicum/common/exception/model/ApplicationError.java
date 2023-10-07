package ru.practicum.common.exception.model;

public interface ApplicationError {
    String getMessage();

    String getReason();

    String getStatus();

    String getTimestamp();
}
