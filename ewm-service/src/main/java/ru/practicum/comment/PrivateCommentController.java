package ru.practicum.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentFullDto;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.EventCommentDto;
import ru.practicum.comment.service.CommentService;
import ru.practicum.common.validation.validationGroup.Create;

@RestController
@RequestMapping("/users/{userId}/comments")
@RequiredArgsConstructor
public class PrivateCommentController {
    private final CommentService commentService;

    @PostMapping("/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentFullDto create(@Validated(Create.class) @RequestBody CommentDto newComment,
                                 @PathVariable("userId") Integer userId,
                                 @PathVariable("eventId") Integer eventId) {
        return commentService.create(newComment, userId, eventId);
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentFullDto edit(@RequestBody CommentDto updateComment,
                               @PathVariable("userId") Integer userId,
                               @PathVariable("commentId") Integer commentId) {
        return commentService.update(updateComment, userId, commentId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("userId") Integer userId,
                       @PathVariable("commentId") Integer commentId) {
        commentService.delete(userId, commentId);
    }

    @GetMapping("{eventId}")
    public EventCommentDto getAll(@PathVariable("userId") Integer userId,
                                  @PathVariable("eventId") Integer eventId) {
        return commentService.getAll(userId, eventId);
    }
}