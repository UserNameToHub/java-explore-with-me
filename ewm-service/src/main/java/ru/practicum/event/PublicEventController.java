package ru.practicum.event;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Constatns.TIME_PATTERN;

@RestController
@RequestMapping("/events")
public class PublicEventController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getAll(@RequestParam("text") String text,
                               @RequestParam("categories") List<Integer> categories,
                               @RequestParam("paid") Boolean paid,
                               @RequestParam("rangeStart") @DateTimeFormat(pattern = TIME_PATTERN) LocalDateTime rangeStart,
                               @RequestParam("rangeEnd") @DateTimeFormat(pattern = TIME_PATTERN) LocalDateTime rangeEnd,
                               @RequestParam(value = "onlyAvailable", defaultValue = "false") Boolean RequestParam,
                               @RequestParam("sort") String sort,
                               @RequestParam(value = "from", defaultValue = "0") Integer from,
                               @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return null;
    }

    @GetMapping("/{id}")
    public EventFullDto getById(@PathVariable("id") Integer id) {
        return null;
    }
}
