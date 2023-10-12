package ru.practicum.event.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.event.dto.LocationDto;
import ru.practicum.event.entity.Location;
import ru.practicum.mapper.BaseMapper;

@Component
public class LocationMapper implements BaseMapper<Location, LocationDto> {
    @Override
    public Location toEntity(LocationDto dto) {
        return Location.builder()
                .lat(dto.getLat())
                .lon(dto.getLon())
                .build();
    }

    @Override
    public LocationDto toDto(Location entity) {
        return LocationDto.builder()
                .lat(entity.getLat())
                .lon(entity.getLon())
                .build();
    }
}