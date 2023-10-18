package ru.practicum.comment.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.comment.dto.CommentFullDto;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.CommentShortDto;
import ru.practicum.comment.dto.EventCommentDto;
import ru.practicum.comment.entity.Comment;
import ru.practicum.common.enumiration.State;
import ru.practicum.event.entity.Event;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.user.entity.User;
import ru.practicum.user.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final UserMapper userMapper;
    private final EventMapper eventMapper;

    public Comment toEntity(CommentDto type, User fElement, Event sElement) {
        return Comment.builder()
                .owner(fElement)
                .event(sElement)
                .createdOn(LocalDateTime.now())
                .text(type.getText())
                .state(State.PENDING)
                .isPositive(type.getIsPositive())
                .build();
    }

    public CommentFullDto toDto(Comment type) {
        return CommentFullDto.builder()
                .id(type.getId())
                .publishedOn(type.getPublishedOn())
                .createdOn(type.getCreatedOn())
                .state(type.getState())
                .text(type.getText())
                .owner(userMapper.toShortDto(type.getOwner()))
                .event(eventMapper.toShortDto(type.getEvent()))
                .isPositive(type.getIsPositive())
                .build();
    }

    public CommentShortDto toCommentShortDto(Comment comment) {
        return CommentShortDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .publishedOn(comment.getPublishedOn())
                .isPositive(comment.getIsPositive())
                .owner(userMapper.toShortDto(comment.getOwner()))
                .build();
    }

    public List<CommentShortDto> toDtoList(Collection<Comment> collection) {
        return collection.stream().map(this::toCommentShortDto).collect(Collectors.toList());
    }

    public EventCommentDto toEventComments(Event event, Collection<Comment> comments) {
        return EventCommentDto.builder()
                .event(eventMapper.toShortDto(event))
                .comments(toDtoList(comments))
                .build();
    }
}
