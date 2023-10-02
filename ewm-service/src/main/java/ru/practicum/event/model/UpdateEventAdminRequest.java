package ru.practicum.event.model;


import lombok.Builder;
import lombok.Data;
import ru.practicum.common.enumiration.StateAction;
import ru.practicum.event.dto.LocationDto;

@Data
@Builder
public class UpdateEventAdminRequest {
    private String annotation;
    private Integer category;
    private String description;
    private LocationDto location;
    private String eventDate;
    private Integer participantLimit;
    private Boolean requestModeration;
    private Boolean paid;
    private String title;
    private StateAction stateAction;
}
