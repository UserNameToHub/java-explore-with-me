package ru.practicum.util;

import org.springframework.data.domain.Sort;

public class Constants {
    public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final Sort ORDER_BY_ID_ASC = Sort.by(Sort.Direction.ASC, "id");
    public static final Sort ORDER_BY_ID_DESC = Sort.by(Sort.Direction.DESC, "id");
}
