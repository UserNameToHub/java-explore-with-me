package ru.practicum.comment.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.comment.dto.CommentFullDto;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.entity.Comment;
import ru.practicum.common.enumiration.State;
import ru.practicum.event.entity.Event;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.user.entity.User;
import ru.practicum.user.mapper.UserMapper;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CommentMapper implements EntityMapper<Comment, CommentDto, User, Event>,
        DtoMapper<CommentFullDto, Comment, Object, Object> {
    private final UserMapper userMapper;
    private final EventMapper eventMapper;

    @Override
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

    @Override
    public CommentFullDto toDto(Comment type, Object fElement, Object sElement) {
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
}
