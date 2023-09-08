package ru.practicum.stats.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.commonDto.HitDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface StatsService {
    ResponseEntity<Object> get(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);

    ResponseEntity<Object> create(HitDto body);
}
