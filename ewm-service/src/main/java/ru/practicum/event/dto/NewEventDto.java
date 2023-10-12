package ru.practicum.event.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.common.validation.annotation.HourAfter;

import javax.validation.constraints.*;

@Data
@Builder
public class NewEventDto {
    @NotBlank
    @Length(min = 20, max = 2000)
    private String annotation;

    @NotNull
    private Integer category;

    @NotBlank
    @Length(min = 20, max = 7000)
    private String description;

    @NotBlank
    @HourAfter(hour = "2")
    private String eventDate;

    @NotNull
    private LocationDto location;

    private Integer participantLimit;

    private Boolean paid;

    private Boolean requestModeration;

    @NotBlank
    @Length(min = 3, max = 120)
    private String title;
}
