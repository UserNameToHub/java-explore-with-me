package ru.practicum.event.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.common.validation.validationGroup.Create;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class NewEventDto {
    @NotBlank
    @Min(value = 20)
    @Max(value = 2000)
    private String annotation;

    @NotNull
    private Integer category;

    @NotBlank
    @Min(value = 20)
    @Max(value = 7000)
    private String description;

    @NotBlank
    private String eventDate;

    @NotNull
    private LocationDto location;

    @NotNull
    private Integer participantLimit;

    private Boolean paid;

    private Boolean requestModeration;

    @NotBlank
    @Min(value = 3)
    @Max(value = 120)
    private String title;
}
