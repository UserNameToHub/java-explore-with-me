package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.UpdateEventUserRequest;
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
    public EventFullDto create(@RequestBody @Valid NewEventDto event,
                               @PathVariable("userId") Integer userId) {
        return eventService.create(event, userId);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getByEventId(@PathVariable("userId") Integer userId,
                                     @PathVariable("eventId") Integer eventId) {
        return eventService.findById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto editByEventId(@RequestBody @Valid UpdateEventUserRequest eventUserRequest,
                                      @PathVariable("userId") Integer userId,
                                      @PathVariable("eventId") Integer eventId) {
        return eventService.update(eventUserRequest, userId, eventId);
    }


    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getAllRequestsByUser(@PathVariable("userId") Integer userId,
                                                              @PathVariable("eventId") Integer eventId) {
        return eventService.findAllRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult editRequestStatus(@RequestBody @Valid EventRequestStatusUpdateRequest eventRequestStatus,
                                                            @PathVariable("userId") Integer userId,
                                                            @PathVariable("eventId") Integer eventId) {
        return eventService.updateRequestStatus(eventRequestStatus, userId, eventId);
    }
}