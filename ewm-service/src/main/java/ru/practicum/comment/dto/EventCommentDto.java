package ru.practicum.comment.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.event.dto.EventShortDto;

import java.util.List;

@Data
@Builder
public class EventCommentDto {
    private EventShortDto event;
    private List<CommentShortDto> comments;
}
