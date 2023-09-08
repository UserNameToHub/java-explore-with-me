package ru.practicum.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.commonDto.HitDto;
import ru.practicum.stats.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@ControllerAdvice(value = "ErrorHandler")
@Slf4j
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    public ResponseEntity<Object> creat(@RequestBody @Valid HitDto shortHit) {
        return statsService.create(shortHit);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getAll(@RequestParam LocalDateTime start,
                                         @RequestParam LocalDateTime end,
                                         @RequestParam List<String> uris,
                                         @RequestParam(defaultValue = "false") boolean unique) {
        return statsService.get(start, end, uris, unique);
    }
}
