package ru.practicum.event.service;

import ru.practicum.common.enumiration.SortEvent;
import ru.practicum.common.enumiration.State;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.EventRequestStatusUpdateRequest;
import ru.practicum.event.model.EventRequestStatusUpdateResult;
import ru.practicum.event.model.UpdateEventAdminRequest;
import ru.practicum.event.model.UpdateEventUserRequest;
import ru.practicum.request.dto.ParticipationRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventShortDto> findAll(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                LocalDateTime rangeEnd, Boolean requestParam, SortEvent sort, Integer from, Integer size,
                                HttpServletRequest servletRequest);

    List<EventFullDto> findAll(List<Integer> usersId, List<State> stats, List<Integer> categories, LocalDateTime rangeStart,
                               LocalDateTime rangeEnd, Integer from, Integer size);

    List<ParticipationRequestDto> findAllRequests(Integer userid, Integer eventId);

    EventFullDto findById(Integer id, State state, HttpServletRequest servletRequest);

    EventFullDto findById(Integer userId, Integer eventId);

    List<EventFullDto> findAllByInitiatorId(Integer userId, Integer from, Integer size);

    EventFullDto create(NewEventDto event, Integer userId);

    EventFullDto update(UpdateEventAdminRequest adminRequest, Integer eventId);

    EventFullDto update(UpdateEventUserRequest newEventDto, Integer userId, Integer eventId);

    EventRequestStatusUpdateResult updateRequestStatus(EventRequestStatusUpdateRequest eventRequest, Integer userId, Integer eventId);
}
