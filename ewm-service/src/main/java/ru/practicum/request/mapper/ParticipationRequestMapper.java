package ru.practicum.request.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.common.enumiration.State;
import ru.practicum.event.entity.Event;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.entity.Request;
import ru.practicum.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ParticipationRequestMapper {
    public Request toEntity(User user, Event event, LocalDateTime timestamp, State status) {
        return Request.builder()
                .created(timestamp)
                .requester(user)
                .event(event)
                .status(status)
                .build();
    }

    public ParticipationRequestDto toDto(Request entity) {
        return ParticipationRequestDto.builder()
                .id(entity.getId())
                .created(entity.getCreated())
                .requester(entity.getRequester().getId())
                .event(entity.getEvent().getId())
                .status(entity.getStatus())
                .build();
    }

    public List<ParticipationRequestDto> toListDto(List<Request> requestList) {
        return requestList.stream().map(this::toDto).collect(Collectors.toList());
    }
}