package ru.practicum.request.dto;

import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
@Valid
public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}
