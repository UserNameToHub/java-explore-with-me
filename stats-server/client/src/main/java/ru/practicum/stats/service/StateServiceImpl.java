package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.commonDto.HitDto;
import ru.practicum.commonDto.HitGettingDto;
import ru.practicum.stats.StatsClient;
import ru.practicum.stats.validation.WrongDateException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.practicum.util.Constants.TIME_PATTERN;

@Service
@RequiredArgsConstructor
@Slf4j
public class StateServiceImpl implements StatsService {
    private final StatsClient statsClient;

    @Override
    public String create(HitDto body) {
        return statsClient.create(body).getBody();
    }

    @Override
    public List<HitGettingDto> getAll(String start, String end, List<String> uris, boolean unique) {
        if (LocalDateTime.parse(start, DateTimeFormatter.ofPattern(TIME_PATTERN))
                .isAfter(LocalDateTime.parse(end, DateTimeFormatter.ofPattern(TIME_PATTERN)))) {
            throw new WrongDateException("Дата начала не может быть позже дате окончания.");
        }
        return statsClient.getAll(start, end, uris, unique).getBody();
    }
}
