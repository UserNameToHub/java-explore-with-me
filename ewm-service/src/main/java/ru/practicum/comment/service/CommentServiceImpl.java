package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.comment.dto.CommentFullDto;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.EventCommentDto;
import ru.practicum.comment.entity.Comment;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.common.enumiration.State;
import ru.practicum.common.exception.ConflictException;
import ru.practicum.common.util.ReflectionChange;
import ru.practicum.event.entity.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.entity.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Constants.TIME_EDITING;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;

    @Override
    public CommentFullDto create(CommentDto newComment, Integer userId, Integer eventId) {
        log.info("Create comment for event with id {} from user with {}", eventId, userId);
        if (!requestRepository.existsByEventIdAndRequesterIdAndStatusIs(userId, eventId, State.CONFIRMED)) {
            throw new ConflictException(String.format("User with id %d is not a participant in the event with id %d",
                    userId, eventId));
        }

        Event event = eventRepository.findByIdAndEventDateBefore(eventId, LocalDateTime.now()).orElseThrow(() ->
                new ConflictException(String.format("Event with id %d is not over yet.", eventId)));

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ConflictException(String.format("User with id %d is not found.", userId)));

        Comment comment = commentMapper.toEntity(newComment, user, event);
        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toDto(savedComment, null, null);
    }

    @Override
    public CommentFullDto update(CommentDto updateComment, Integer userId, Integer commentId) {
        log.info("Owner with id {} update comment with id {}", userId, commentId);
        if (!userRepository.existsById(userId)) {
            throw new ConflictException(String.format("User with id %d is not found.", userId));
        }

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ConflictException(String.format("Comment with id %d is not found.", commentId)));

        if (!commentRepository.existsByIdAndOwnerId(commentId, userId)) {
            throw new ConflictException(String.format("User with id %d is not a owner for comment with id %d.",
                    userId, commentId));
        }

        LocalDateTime eventDate = comment.getPublishedOn();
        LocalDateTime endEditing = eventDate.plusHours(TIME_EDITING);

        if (endEditing.isBefore(LocalDateTime.now())) {
            throw new ConflictException(String.format("Edit time is over."));
        }

        Comment updatedComment = ReflectionChange.go(comment, updateComment);
        updatedComment.setState(State.PENDING);
        Comment savedComment = commentRepository.saveAndFlush(updatedComment);

        return commentMapper.toDto(savedComment, null, null);
    }

    @Override
    public void delete(Integer userId, Integer commentId) {
        log.info("Owner with id {} delete comment with id {}", userId, commentId);
        if (!commentRepository.existsByIdAndOwnerId(commentId, userId)) {
            throw new ConflictException(String.format("User with id %d is not a owner for comment with id %d.",
                    userId, commentId));
        }

        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentFullDto update(Integer commentId, State state) {
        log.info("Admin update comment with id {}", commentId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ConflictException(String.format("Comment with id %d is not found.")));

        comment.setState(state);
        Comment savedComment = commentRepository.saveAndFlush(comment);

        return commentMapper.toDto(savedComment, null, null);
    }

    @Override
    public EventCommentDto getAll(Integer userId, Integer eventId) {
        if (!userRepository.existsById(userId)) {
            throw new ConflictException(String.format("User with id %d is not found."));
        }

        Event event = eventRepository.findByIdAndEventDateBefore(eventId, LocalDateTime.now()).orElseThrow(() ->
                new ConflictException(String.format("Event with id %id is not over yet.", eventId)));

        List<Comment> comments = commentRepository.findAllByEventIdAndStateIs(eventId, State.CONFIRMED);

        return commentMapper.toEventComments(event, comments);
    }
}