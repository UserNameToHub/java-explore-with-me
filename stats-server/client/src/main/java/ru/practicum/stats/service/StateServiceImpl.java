package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.commonDto.HitDto;
import ru.practicum.stats.StatsClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StateServiceImpl implements StatsService {
    private final StatsClient statsClient;

    @Override
    public ResponseEntity<Object> get(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        log.info("Get hitGetting.");
        return statsClient.getAll(start, end, uris, unique);
    }

    @Override
    public ResponseEntity<Object> create(HitDto body) {
        log.info("Creating hit with app {}, uri {}, ip {}, timestamp {}",
                body.getApp(), body.getUri(), body.getIp(), body.getTimestamp());
        return statsClient.create(body);
    }
}
