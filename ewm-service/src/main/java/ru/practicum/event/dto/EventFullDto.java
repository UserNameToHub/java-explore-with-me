package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.category.Dto.CategoryDto;
import ru.practicum.common.enumiration.State;
import ru.practicum.user.dto.UserShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.util.Constatns.TIME_PATTERN;

@Data
@Builder
public class EventFullDto {
    @NotBlank(message = "Данное поле не долно быть пустым.")
    private String annotation;

    @NotNull(message = "Данное поле не долно быть пустым.")
    private CategoryDto category;

    private Integer confirmedRequests;

    private String createdOn;

    private String description;

    @JsonFormat(pattern = TIME_PATTERN)
    @NotNull(message = "Данное поле не долно быть пустым.")
    private LocalDateTime eventDate;

    private Integer id;

    @NotBlank(message = "Данное поле не долно быть пустым.")
    private UserShortDto initiator;

    @NotNull
    private LocationDto location;

    @NotNull
    private Boolean paid;

    @Builder.Default
    private Integer participantLimit = 0;

    @JsonFormat(pattern = TIME_PATTERN)
    private LocalDateTime publishedOn;

    @Builder.Default
    private Boolean requestModeration = true;

    private State state;

    @NotBlank
    private String title;

    private Integer views;
}
