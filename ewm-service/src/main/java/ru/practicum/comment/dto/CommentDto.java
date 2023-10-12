package ru.practicum.comment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
    private String text;
    private Boolean isPositive;
}
