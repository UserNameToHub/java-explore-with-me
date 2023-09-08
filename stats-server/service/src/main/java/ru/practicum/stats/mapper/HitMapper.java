package ru.practicum.stats.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.commonDto.HitDto;
import ru.practicum.mapper.BaseMapper;
import ru.practicum.stats.entity.EndpointHit;

@Component
public class HitMapper implements BaseMapper<EndpointHit, HitDto> {
    @Override
    public EndpointHit toEntity(HitDto dto) {
        return EndpointHit.builder()
                .app(dto.getApp())
                .uri(dto.getUri())
                .ip(dto.getIp())
                .timestamp(dto.getTimestamp())
                .build();
    }

    @Override
    public HitDto toDto(EndpointHit entity) {
        return HitDto.builder()
                .id(entity.getId())
                .app(entity.getApp())
                .uri(entity.getUri())
                .timestamp(entity.getTimestamp())
                .build();
    }
}
