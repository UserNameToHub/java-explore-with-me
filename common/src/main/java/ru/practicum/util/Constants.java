package ru.practicum.util;

import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

public class Constants {
    public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final Sort ORDER_BY_ID_ASC = Sort.by(Sort.Direction.ASC, "id");
    public static final Sort ORDER_BY_EVENT_DATE_ASC = Sort.by(Sort.Direction.ASC, "event_date");
    public static final Sort ORDER_BY_ID_DESC = Sort.by(Sort.Direction.DESC, "id");
    public static final LocalDateTime START_DATE = LocalDateTime.of(1900, 01,
            01, 00, 00, 01);
    public static final LocalDateTime END_DATE = LocalDateTime.of(3000, 01,
            01, 00, 00, 01);
    public static final String REASON_NOT_FOUND = "The required object was not found.";
    public static final String REASON_BED_REQUEST = "Incorrectly made request.";
    public static final String REASON_CONFLICT = "Integrity constraint has been violated.";
}
