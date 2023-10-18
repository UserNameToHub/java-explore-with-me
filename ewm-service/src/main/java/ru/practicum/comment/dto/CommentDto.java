package ru.practicum.comment.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.common.validation.validationGroup.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CommentDto {
    @NotBlank(groups = Create.class)
    @Length(min = 10, max = 500, groups = Create.class)
    private String text;

    @NotNull(groups = Create.class)
    private Boolean isPositive;
}
