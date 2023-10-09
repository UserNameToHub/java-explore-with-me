package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.enumiration.State;
import ru.practicum.common.exception.ConflictException;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.event.entity.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.entity.Request;
import ru.practicum.request.mapper.ParticipationRequestMapper;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.entity.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ParticipationRequestMapper participationRequestMapper;


    @Override
    @Transactional
    public ParticipationRequestDto create(Integer userId, Integer eventId) {
        log.info("Creating request with userId {} and eventId {}", userId, eventId);

        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%d was not found", userId)));
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with id=%d was not found", eventId)));

        if (requestRepository.existRepeatingRequest(eventId, userId)) {
            throw new ConflictException("Такой запрос уже существует.");
        }

        if (event.getInitiator().getId().equals(user.getId())) {
            throw new ConflictException("Инициатор события не может создать запрос.");
        }

        if (!eventRepository.existsByIdAndStateIs(eventId, State.PUBLISHED)) {
            throw new ConflictException("Создать запрос можно только на опубликованное событие.");
        }

        if (requestRepository.findConfirmedRequestsCount(eventId, State.CONFIRMED) >= event.getParticipantLimit() &&
                event.getParticipantLimit() != 0) {
            throw new ConflictException("Лимит запросов уже достигнут.");
        }

        ParticipationRequestDto participationRequestDto = participationRequestMapper.toDto(requestRepository.save(participationRequestMapper
                .toEntity(user, event, LocalDateTime.now(), event.getRequestModeration() == true &&
                        event.getParticipantLimit() > 0 ? State.PENDING : State.CONFIRMED)));
        return participationRequestDto;
    }

    @Override
    public List<ParticipationRequestDto> findAllById(Integer userId) {
        log.info("Get request with userId {}.", userId);
        return participationRequestMapper.toListDto(requestRepository.findAllByUserId(userId));
    }

    @Override
    @Transactional
    public ParticipationRequestDto edit(Integer userId, Integer requestId) {
        log.info("Editing request with userId {} and requestId.", userId, requestId);
        Request request = requestRepository.findById(requestId).orElseThrow(() ->
                new NotFoundException(String.format("Request with id=%d was not found", requestId)));

        request.setStatus(State.CANCELED);

        return participationRequestMapper.toDto(requestRepository.saveAndFlush(request));
    }
}