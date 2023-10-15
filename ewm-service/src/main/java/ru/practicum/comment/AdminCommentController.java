package ru.practicum.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentFullDto;
import ru.practicum.comment.service.CommentService;
import ru.practicum.common.enumiration.State;
import ru.practicum.common.validation.annotation.EnumNamePattern;

@RestController
@RequestMapping("/admin/comments/{commentId}")
@RequiredArgsConstructor
public class AdminCommentController {
    private final CommentService commentService;

    @PatchMapping("/confirmation")
    public CommentFullDto editStatus(@PathVariable("commentId") Integer commentId,
                                     @RequestParam("state") @EnumNamePattern(regexp = "CONFIRMED|REJECTED") State state) {
        return commentService.update(commentId, state);
    }
}