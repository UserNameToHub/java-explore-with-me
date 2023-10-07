package ru.practicum.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.common.enumiration.StateAction;
import ru.practicum.common.validation.annotation.FutureDate;
import ru.practicum.event.dto.LocationDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventAdminRequest {
    @Length(min = 20, max = 2000)
    private String annotation;

    private Integer category;

    @Length(min = 20, max = 7000)
    private String description;

    private LocationDto location;

    @FutureDate()
    private String eventDate;

    private Integer participantLimit;

    private Boolean requestModeration;

    private Boolean paid;

    @Length(min = 3, max = 120)
    private String title;

    private StateAction stateAction;
}
