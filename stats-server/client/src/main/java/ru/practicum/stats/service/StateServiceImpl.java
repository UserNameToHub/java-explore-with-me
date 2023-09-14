package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.commonDto.HitDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class StateServiceImpl implements StatsService {
    @Override
    public HitDto create(HitDto body) {
        log.info("Creating hit with app {}, uri {}, ip {}, timestamp {}",
                body.getApp(), body.getUri(), body.getIp(), body.getTimestamp());
        return body;
    }
}
