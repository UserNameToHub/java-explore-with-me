package ru.practicum.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.practicum.commonDto.HitDto;
import ru.practicum.commonDto.HitGettingDto;
import ru.practicum.stats.service.StatsService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@ControllerAdvice(value = "ErrorHandler")
@Slf4j
public class StatsController {
    private final StatsService statsService;
    private final StatsClient statsClient;

    @PostMapping("/hit")
    public ResponseEntity<String> create(@RequestBody @Valid HitDto shortHit) {
        return statsClient.create(statsService.create(shortHit));
    }

    @GetMapping("/stats")
    public ResponseEntity<List<HitGettingDto>> getAll(@RequestParam String start,
                                                      @RequestParam String end,
                                                      @RequestParam(required = false) List<String> uris,
                                                      @RequestParam(defaultValue = "false") boolean unique) {
        return statsClient.getAll(start, end, uris, unique);
    }
}