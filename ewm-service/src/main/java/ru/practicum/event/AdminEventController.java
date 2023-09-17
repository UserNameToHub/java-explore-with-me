package ru.practicum.event;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.common.enumiration.State;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Constatns.TIME_PATTERN;

@RestController
@RequestMapping("/admin/events")
public class AdminEventController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getAll(@RequestParam("users") List<Integer> users,
                                     @RequestParam("stats")List<State> states,
                                     @RequestParam("categories") List<Integer> categories,
                                     @RequestParam("rangeStart") @DateTimeFormat(pattern = TIME_PATTERN) LocalDateTime rangeStart,
                                     @RequestParam("rangeEnd") @DateTimeFormat(pattern = TIME_PATTERN) LocalDateTime rangeEnd,
                                     @RequestParam(value = "from", defaultValue = "0") Integer from,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return null;
    }

    @PatchMapping("/{eventId")
    public EventFullDto edit(@RequestBody @Valid EventShortDto event,
                             @PathVariable("eventId") Integer eventId) {
        return null;
    }
}
