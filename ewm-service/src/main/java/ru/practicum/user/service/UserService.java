package ru.practicum.user.service;

import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll(List<Integer> ids, Integer from, Integer size);

    UserDto create(NewUserRequest userShortDto);

    void delete(Integer userId);
}
