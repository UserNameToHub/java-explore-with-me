package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.Dto.CategoryDto;
import ru.practicum.common.enumiration.State;
import ru.practicum.user.dto.UserShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.TIME_PATTERN;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    private Integer participantLimit;

    @JsonFormat(pattern = TIME_PATTERN)
    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private State state;

    @NotBlank
    private String title;

    private Integer views;
}
