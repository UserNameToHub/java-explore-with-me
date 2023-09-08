package ru.practicum.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface BaseMapper<T1, T2> {
    T1 toEntity(T2 dto);

    T2 toDto(T1 entity);

    default List<T2> toDtoList(List<T1> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
