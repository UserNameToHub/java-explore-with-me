package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.category.Dto.CategoryDto;
import ru.practicum.common.validation.annotation.CreationDateConstraint;
import ru.practicum.common.validation.validationGroup.Create;
import ru.practicum.user.dto.UserShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.util.Constatns.TIME_PATTERN;

@Data
@Builder
public class EventShortDto {
    @NotBlank
    private String annotation;

    @NotNull
    private CategoryDto category;

    private Integer confirmedRequests;

    @NotNull
    @JsonFormat(pattern = TIME_PATTERN)
    @CreationDateConstraint(hour = "2", groups = Create.class)
    private LocalDateTime eventDate;

    private Integer id;

    @NotNull
    private UserShortDto initiator;

    @NotNull
    private Boolean paid;

    @NotBlank
    private String title;

    private Integer views;
}