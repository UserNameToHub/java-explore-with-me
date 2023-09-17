package ru.practicum.event;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.common.validation.validationGroup.Create;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.ParticipationRequestDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {
    @GetMapping
    public List<EventFullDto> getAllByUserId(@PathVariable("userId") Integer userId,
                                             @RequestParam(value = "from", defaultValue = "0") Integer from,
                                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventShortDto create(@RequestBody @Valid @Validated(Create.class) EventShortDto event,
                                @PathVariable("userId") Integer userId) {
        return null;
    }

    @GetMapping("/{eventId")
    private EventFullDto getAllByEventId(@PathVariable("userId") Integer userId,
                                         @PathVariable("eventId") Integer eventId) {
        return null;
    }

    @PatchMapping("/{eventId")
    private EventFullDto getAllByEventId(@RequestBody @Valid @Validated(Create.class) EventShortDto event,
                                         @PathVariable("userId") Integer userId,
                                         @PathVariable("eventId") Integer eventId) {
        return null;
    }


    @GetMapping("/{eventId/requests")
    private List<ParticipationRequestDto> getAllRequestsByUser(@PathVariable("userId") Integer userId,
                                                               @PathVariable("eventId") Integer eventId) {
        return null;
    }

    @PatchMapping("/{eventId/requests")
    private EventRequestStatusUpdateResult editRequestStatus(@RequestBody @Valid EventRequestStatusUpdateRequest eventRequestStatus,
                                                             @PathVariable("userId") Integer userId,
                                                             @PathVariable("eventId") Integer eventId) {
        return null;
    }
}