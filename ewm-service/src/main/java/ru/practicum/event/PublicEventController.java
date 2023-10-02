package ru.practicum.event;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.common.enumiration.State;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Constants.TIME_PATTERN;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class PublicEventController {
    private final EventService eventService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getAll(@RequestParam(value = "text", required = false) String text,
                                      @RequestParam(value = "categories", required = false) List<Integer> categories,
                                      @RequestParam(value = "paid", required = false) Boolean paid,
                                      @RequestParam(value = "rangeStart", required = false) @DateTimeFormat(pattern = TIME_PATTERN) LocalDateTime rangeStart,
                                      @RequestParam(value = "rangeEnd", required = false) @DateTimeFormat(pattern = TIME_PATTERN) LocalDateTime rangeEnd,
                                      @RequestParam(value = "onlyAvailable", defaultValue = "false") Boolean RequestParam,
                                      @RequestParam(value = "sort", required = false) String sort,
                                      @RequestParam(value = "from", defaultValue = "0") Integer from,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size,
                                      HttpServletRequest request) {
        return eventService.findAll(text, categories, paid, rangeStart, rangeEnd, RequestParam, sort, from, size, request);
    }

    @GetMapping("/{id}")
    public EventFullDto getById(@PathVariable("id") Integer id, HttpServletRequest request) {
        return eventService.findById(id, State.PUBLISHED, request);
    }
}
