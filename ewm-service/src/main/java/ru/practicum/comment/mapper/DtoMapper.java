package ru.practicum.comment.mapper;

import org.springframework.lang.Nullable;

public interface DtoMapper<T1, T2, E1, E2> {
    T1 toDto(T2 type, @Nullable E1 fElement, @Nullable E2 sElement);
}