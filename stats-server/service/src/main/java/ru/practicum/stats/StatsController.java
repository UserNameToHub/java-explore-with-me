package ru.practicum.stats;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.commonDto.HitDto;
import ru.practicum.commonDto.HitGettingDto;
import ru.practicum.stats.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Constatns.TIME_PATTERN;

@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public String creat(@RequestBody @JsonFormat(pattern = TIME_PATTERN) HitDto shortHit) {
        statsService.creat(shortHit);
        return "Данные успешно записаны.";
    }

    @GetMapping("/stats")
    public List<HitGettingDto> getAll(@RequestParam  @DateTimeFormat(pattern = TIME_PATTERN) LocalDateTime start,
                                      @RequestParam @DateTimeFormat(pattern = TIME_PATTERN) LocalDateTime end,
                                      @RequestParam(required = false)  List<String> uris,
                                      @RequestParam(defaultValue = "false") boolean unique) {
        return statsService.getAll(start, end, uris, unique);
    }
}
