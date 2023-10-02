package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.entity.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.common.enumiration.State;
import ru.practicum.common.enumiration.StateAction;
import ru.practicum.common.exception.ConflictException;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.util.ReflectionChange;
import ru.practicum.event.StatsClient;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.HitGettingDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.entity.Event;
import ru.practicum.event.entity.Location;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.UpdateEventAdminRequest;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.LocationRepository;
import ru.practicum.mapper.ModelMapper;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.entity.User;
import ru.practicum.user.repository.UserRepository;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.util.Constants.ORDER_BY_ID_ASC;
import static ru.practicum.util.Constants.TIME_PATTERN;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final StatsClient statsClient;
    private final ModelMapper modelMapper;
    private final EventMapper eventMapper;


    @Override
    public List<EventShortDto> findAll(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd, Boolean requestParam, String sort, Integer from, Integer size,
                                       HttpServletRequest servletRequest) {
        List<Event> events = eventRepository.findAll(text, categories, paid,
                rangeStart, rangeEnd, requestParam, PageRequest.of(from, size)).toList();

        List<EventShortDto> eventShortDtos = eventMapper.toShortDtoList(events);
        List<String> uris = getUris(eventShortDtos);

        Map<Integer, Long> views = getViews(eventShortDtos, rangeStart, rangeEnd, servletRequest);

        uris.stream().forEach(e -> {
            try {
                statsClient.create("ewm-service", e, servletRequest.getRemoteAddr(), LocalDateTime.now());
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
        });

        eventShortDtos.stream().forEach(e -> {
            e.setViews(views.isEmpty() ? 0: views.get(e.getId()).intValue());
            e.setConfirmedRequests(requestRepository.findConfirmedRequestsCount(e.getId(), State.CONFIRMED));
        });

        return eventShortDtos;
    }

    @Override
    public List<EventFullDto> findAll(List<Integer> usersId, List<State> stats, List<Integer> categories,
                                      LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        return modelMapper.doListMapping(eventRepository.findAll(usersId, stats, categories, rangeStart, rangeEnd,
                PageRequest.of(from, size, ORDER_BY_ID_ASC)).toList(), EventFullDto.class);
    }

    @Override
    public List<EventFullDto> findAllByInitiatorId(Integer userId, Integer from, Integer size) {
        return eventMapper.toFullDtoList(eventRepository.findAllByInitiatorId(userId,
                PageRequest.of(from, size, ORDER_BY_ID_ASC)).toList(), 0, 0);
    }

    @Override
    public EventFullDto findById(Integer id, State state, HttpServletRequest servletRequest) {
        Event event = eventRepository.findByIdAndStateIs(id, state).orElseThrow(() ->
                new NotFoundException(String.format("Event with id=%d was not found", id)));
        try {
            statsClient.create("ewm-service", "/events/" + id , servletRequest.getRemoteAddr(), LocalDateTime.now());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return eventMapper.toDto1(event, 0, 0);
    }

    @Override
    @Transactional
    public EventFullDto create(NewEventDto event, Integer userId) {
        log.info("Creating event with annotation {} and description {}", event.getAnnotation(), event.getDescription());

        Category category = categoryRepository.findById(event.getCategory()).orElseThrow(() ->
                new NotFoundException(String.format("Category with id=%d was not found", event.getCategory())));
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%d was not found", userId)));
        Location location = locationRepository.findByLatAndLon(event.getLocation().getLat(), event.getLocation().getLon())
                .orElse(locationRepository.save(modelMapper.doMapping(event.getLocation(), Location.builder().build())));

        return eventMapper.toDto(eventRepository.save(eventMapper.toEntity(event, user, category, location)), 0, 0, location);
    }

    @Override
    @Transactional
    public EventFullDto update(UpdateEventAdminRequest adminRequest, Integer eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with id=%d was not found", eventId)));

        Event updatingEvent = updateEvent(event, adminRequest);

        return eventMapper.toDto(updatingEvent, 0, 0, updatingEvent.getLocation());
    }

    @Override
    @Transactional
    public EventFullDto update(NewEventDto newEventDto, Integer userId, Integer eventId) {
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId).orElseThrow(() ->
                new NotFoundException(String.format("Event with id=%d was not found", eventId)));

        if (event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Event must not be published");
        }

        Event updatingEvent = ReflectionChange.go(event, newEventDto);

        if (Objects.nonNull(newEventDto.getCategory())) {
            Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() ->
                    new NotFoundException(String.format("Category with id=%d was not found", newEventDto.getCategory())));
            updatingEvent.setCategory(category);
        }

        if (Objects.nonNull(newEventDto.getEventDate())) {
            updatingEvent.setEventDate(LocalDateTime.parse(newEventDto.getEventDate(), DateTimeFormatter.ofPattern(TIME_PATTERN)));
        }

        if (Objects.nonNull(newEventDto.getLocation())) {
            Location location = locationRepository.findByLatAndLon(newEventDto.getLocation().getLat(),
                    newEventDto.getLocation().getLon()).orElse(locationRepository.save(
                            modelMapper.doMapping(newEventDto.getLocation(), Location.builder().build())));
            updatingEvent.setLocation(location);
        }

        return eventMapper.toDto1(eventRepository.saveAndFlush(updatingEvent), 0, 0);
    }

    private Map<Integer, Long> getViews(List<EventShortDto> eventDtos, @Nullable LocalDateTime start,
                                        @Nullable LocalDateTime end, HttpServletRequest request) {
        Map<Integer, Long> views = new HashMap<>();

        try {
            List<HitGettingDto> hitList = statsClient.getAll(start, end, getUris(eventDtos), true, request);
            hitList.stream().forEach(e -> {
                var id = e.getUri().split("/");
                views.put(Integer.parseInt(id[id.length - 1]), e.getHits());
            });
            return views;
        } catch (JSONException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private List<String> getUris(List<EventShortDto> eventDtos) {
        return eventDtos.stream()
                .map(e -> "/events/" + e.getId())
                .collect(Collectors.toList());
    }

    private Event updateEvent(Event event, UpdateEventAdminRequest updateEvent) {
        Field[] declaredFieldsUpdateEvent = updateEvent.getClass().getDeclaredFields();

        Arrays.stream(declaredFieldsUpdateEvent)
                .filter(o -> {
                    o.setAccessible(true);
                    try {
                       return Objects.nonNull(o.get(updateEvent));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }).forEach(e -> {
                    e.setAccessible(true);
                    try {
                        if (e.getName().equals("stateAction")) {
                            if (!event.getState().equals(State.PENDING) && updateEvent.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
                                throw new ConflictException("Cannot publish the event because it's not in the right state: PUBLISHED");
                            }
                            if (event.getState().equals(State.PUBLISHED) && updateEvent.getStateAction().equals(StateAction.REJECT_EVENT)) {
                                throw new ConflictException("Cannot reject the event because it's in the right state: PUBLISHED");
                            }
                            Field state = event.getClass().getDeclaredField("state");
                            Field publishedOn = event.getClass().getDeclaredField("publishedOn");
                            state.setAccessible(true);
                            publishedOn.setAccessible(true);
                            state.set(event, e.get(updateEvent).equals(StateAction.PUBLISH_EVENT) ? State.PUBLISHED : State.CANCELED);
                            publishedOn.set(event, LocalDateTime.now());
                        } else if (e.getName().equals("category")) {
                            Category category = categoryRepository.findById(updateEvent.getCategory()).orElseThrow(() ->
                                    new ConflictException(String.format("Category with id=%d was not found", updateEvent.getCategory())));
                            Field categoryEvent = event.getClass().getDeclaredField("category");
                            categoryEvent.setAccessible(true);
                            categoryEvent.set(event, category);
                        } else if (e.getName().equals("location")) {
                            Location location = locationRepository.saveAndFlush(modelMapper.doMapping(updateEvent.getLocation(), Location.builder().build()));
                            Field categoryEvent = event.getClass().getDeclaredField("location");
                            categoryEvent.setAccessible(true);
                            categoryEvent.set(event, location);
                        } else if (e.getName().equals("eventDate")) {
                            if (!LocalDateTime.now().plusHours(1).isBefore(LocalDateTime.parse(updateEvent.getEventDate(),
                                    DateTimeFormatter.ofPattern(TIME_PATTERN)))) {
                                throw new ConflictException(String.format("должно содержать дату, которая еще не наступила. Value: %s", updateEvent.getEventDate()));
                            }
                            Field categoryEvent = event.getClass().getDeclaredField("eventDate");
                            categoryEvent.setAccessible(true);
                            categoryEvent.set(event, LocalDateTime.parse(e.get(updateEvent).toString(), DateTimeFormatter.ofPattern(TIME_PATTERN)));
                        } else {
                            Field matchField = event.getClass().getDeclaredField(e.getName());
                            matchField.setAccessible(true);
                            matchField.set(event, e.get(updateEvent));
                        }
                    } catch (NoSuchFieldException ex) {
                        throw new RuntimeException(ex);
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                });

        return event;
    }
}