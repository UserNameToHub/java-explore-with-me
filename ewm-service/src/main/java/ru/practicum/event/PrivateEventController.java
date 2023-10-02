package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.common.validation.validationGroup.Create;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.service.EventService;
import ru.practicum.event.model.EventRequestStatusUpdateRequest;
import ru.practicum.event.model.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.ParticipationRequestDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getAllByUserId(@PathVariable("userId") Integer userId,
                                             @RequestParam(value = "from", defaultValue = "0") Integer from,
                                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return eventService.findAllByInitiatorId(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@RequestBody NewEventDto event,
                               @PathVariable("userId") Integer userId) {
        return eventService.create(event, userId);
    }

    @GetMapping("/{eventId}")
    private EventFullDto getByEventId(@PathVariable("userId") Integer userId,
                                      @PathVariable("eventId") Integer eventId) {
        return null;
    }

    @PatchMapping("/{eventId}")
    private EventFullDto editByEventId(@RequestBody @Valid @Validated(Create.class) NewEventDto event,
                                      @PathVariable("userId") Integer userId,
                                      @PathVariable("eventId") Integer eventId) {
        return eventService.update(event, userId, eventId);
    }


    @GetMapping("/{eventId}/requests")
    private List<ParticipationRequestDto> getAllRequestsByUser(@PathVariable("userId") Integer userId,
                                                               @PathVariable("eventId") Integer eventId) {
        return null;
    }

    @PatchMapping("/{eventId}/requests")
    private EventRequestStatusUpdateResult editRequestStatus(@RequestBody @Valid EventRequestStatusUpdateRequest eventRequestStatus,
                                                             @PathVariable("userId") Integer userId,
                                                             @PathVariable("eventId") Integer eventId) {
        return null;
    }
}