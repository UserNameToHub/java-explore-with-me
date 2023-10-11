package ru.practicum.stats.service;

import org.springframework.stereotype.Service;
import ru.practicum.commonDto.HitDto;
import ru.practicum.commonDto.HitGettingDto;

import java.util.List;

@Service
public interface StatsService {
    String create(HitDto body);

    List<HitGettingDto> getAll(String start, String end, List<String> uris, boolean unique);
}
