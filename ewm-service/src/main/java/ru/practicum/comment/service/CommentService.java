package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentFullDto;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.EventCommentDto;
import ru.practicum.common.enumiration.State;

public interface CommentService {
    CommentFullDto create(CommentDto newComment, Integer userId, Integer eventId);

    CommentFullDto update(CommentDto updateComment, Integer userId, Integer commentId);

    void delete(Integer userId, Integer commentId);

    CommentFullDto update(Integer commentId, State state);

    EventCommentDto getAll(Integer userId, Integer eventId);
}