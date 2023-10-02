package ru.practicum.event.service;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.common.enumiration.State;
import ru.practicum.common.validation.validationGroup.Create;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.UpdateEventAdminRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventShortDto> findAll(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                LocalDateTime rangeEnd, Boolean requestParam, String sort, Integer from, Integer size,
                                HttpServletRequest servletRequest);

    List<EventFullDto> findAll(List<Integer> usersId, List<State> stats, List<Integer> categories, LocalDateTime rangeStart,
                                LocalDateTime rangeEnd, Integer from, Integer size);

    EventFullDto findById(Integer id, State state, HttpServletRequest servletRequest);

    List<EventFullDto> findAllByInitiatorId(Integer userId, Integer from, Integer size);

    EventFullDto create(NewEventDto event, Integer userId);

    EventFullDto update(UpdateEventAdminRequest adminRequest, Integer eventId);

    EventFullDto update(NewEventDto newEventDto, Integer userId, Integer eventId);

}
