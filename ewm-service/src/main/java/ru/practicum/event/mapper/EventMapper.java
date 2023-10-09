package ru.practicum.event.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.category.Dto.CategoryDto;
import ru.practicum.category.entity.Category;
import ru.practicum.common.enumiration.State;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.LocationDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.entity.Event;
import ru.practicum.event.entity.Location;
import ru.practicum.mapper.ModelMapper;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.entity.User;
import ru.practicum.user.mapper.UserMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.practicum.util.Constants.TIME_PATTERN;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final ModelMapper modelMapper;
    private final UserMapper userMapper;

    public EventShortDto toShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(event.getCategory())
                .paid(event.getPaid())
                .eventDate(event.getEventDate().format(DateTimeFormatter.ofPattern(TIME_PATTERN)))
                .title(event.getTitle())
                .initiator(userMapper.toShortDto(event.getInitiator()))
                .build();


    }

    public EventFullDto toDto(Event event, Integer views, Integer requests, Location location) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(modelMapper.doMapping(event.getCategory(), CategoryDto.builder().build()))
                .location(Objects.nonNull(location) ? modelMapper.doMapping(location, LocationDto.builder().build()) :
                        modelMapper.doMapping(event.getLocation(), LocationDto.builder().build()))
                .createdOn(event.getCreatedOn().toString())
                .description(event.getDescription())
                .paid(event.getPaid())
                .publishedOn(event.getPublishedOn())
                .eventDate(event.getEventDate())
                .title(event.getTitle())
                .requestModeration(event.getRequestModeration())
                .confirmedRequests(requests)
                .initiator(modelMapper.doMapping(event.getInitiator(), UserShortDto.builder().build()))
                .views(views)
                .participantLimit(event.getParticipantLimit())
                .state(event.getState())
                .build();
    }

    public EventFullDto toDtoWithoutLocation(Event event, Integer views, Integer requests) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(modelMapper.doMapping(event.getCategory(), CategoryDto.builder().build()))
                .location(modelMapper.doMapping(event.getLocation(), LocationDto.builder().build()))
                .createdOn(event.getCreatedOn().toString())
                .description(event.getDescription())
                .paid(event.getPaid())
                .publishedOn(event.getPublishedOn())
                .eventDate(event.getEventDate())
                .title(event.getTitle())
                .confirmedRequests(requests)
                .initiator(modelMapper.doMapping(event.getInitiator(), UserShortDto.builder().build()))
                .views(views)
                .participantLimit(event.getParticipantLimit())
                .state(event.getState())
                .build();
    }

    public Event toEntity(NewEventDto event, User initiator, Category category, Location location) {
        return Event.builder()
                .annotation(event.getAnnotation())
                .category(category)
                .description(event.getDescription())
                .location(location)
                .initiator(initiator)
                .createdOn(LocalDateTime.now())
                .eventDate(LocalDateTime.parse(event.getEventDate(), DateTimeFormatter.ofPattern(TIME_PATTERN)))
                .requestModeration(event.getRequestModeration())
                .state(State.PENDING)
                .title(event.getTitle())
                .participantLimit(Objects.nonNull(event.getParticipantLimit()) ? event.getParticipantLimit() : 0)
                .paid(Objects.nonNull(event.getPaid()) ? event.getPaid() : false)
                .requestModeration(Objects.nonNull(event.getRequestModeration()) ? event.getRequestModeration() : true)
                .build();
    }

    public List<EventFullDto> toFullDtoList(List<Event> events, Map<Integer, Long> views, Integer requests) {
        return events.stream().map(e -> this.toDtoWithoutLocation(e, Objects.isNull(views.get(e.getId())) ? 0 :
                        views.get(e.getId()).intValue(), requests))
                .collect(Collectors.toList());
    }

    public List<EventShortDto> toShortDtoList(List<Event> events) {
        return events.stream().map(this::toShortDto).collect(Collectors.toList());
    }
}