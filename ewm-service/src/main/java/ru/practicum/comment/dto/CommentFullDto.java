package ru.practicum.comment.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.common.enumiration.State;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentFullDto {
    private Integer id;
    private UserShortDto owner;
    private EventShortDto event;
    private LocalDateTime publishedOn;
    private LocalDateTime createdOn;
    private String text;
    private Boolean isPositive;
    private State state;
}
