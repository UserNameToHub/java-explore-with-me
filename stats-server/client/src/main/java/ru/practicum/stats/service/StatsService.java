package ru.practicum.stats.service;

import org.springframework.stereotype.Service;
import ru.practicum.commonDto.HitDto;

@Service
public interface StatsService {
    HitDto create(HitDto body);
}
