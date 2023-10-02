package ru.practicum.request.service;

import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    ParticipationRequestDto create(Integer userId, Integer eventId);

    List<ParticipationRequestDto> findAllById(Integer userId);

    ParticipationRequestDto edit(Integer userId, Integer requestId);
}
