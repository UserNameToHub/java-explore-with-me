package ru.practicum.event.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.common.enumiration.State;

import java.util.List;

@Data
@Builder
public class EventRequestStatusUpdateRequest {
    private List<Integer> requestIds;
    private State status;
}
