package ru.practicum.stats.service;

import ru.practicum.commonDto.HitDto;
import ru.practicum.commonDto.HitGettingDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void creat(HitDto shortHit);

    List<HitGettingDto> getAll(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}