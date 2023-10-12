package ru.practicum.comment.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentShortDto {
    private Integer id;
    private UserShortDto owner;
    private LocalDateTime publishedOn;
    private String text;
    private Boolean isPositive;
}
