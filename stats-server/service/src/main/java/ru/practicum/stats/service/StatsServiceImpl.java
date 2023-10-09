package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.commonDto.HitDto;
import ru.practicum.commonDto.HitGettingDto;
import ru.practicum.stats.entity.EndpointHit;
import ru.practicum.stats.mapper.HitMapper;
import ru.practicum.stats.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;
    private final HitMapper mapper;

    @Override
    @Transactional
    public void creat(HitDto shortHit) {
        mapper.toDto(repository.save(mapper.toEntity(shortHit)));
    }

    @Override
    public List<HitGettingDto> getAll(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<HitGettingDto> allStats = repository.findAllStats(start, end, uris, unique);
//        boolean b = repository.existsByUriIn(uris);
        List<EndpointHit> all = repository.findAll();
        return repository.findAllStats(start, end, uris, unique);
    }
}
