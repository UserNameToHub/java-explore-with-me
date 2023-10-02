package ru.practicum.compilation.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.entity.Compilation;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.entity.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.mapper.ModelMapper;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper {
    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;

    public Compilation toEntity(NewCompilationDto newCompilationDto)  {
        var events = newCompilationDto.getEvents().stream()
                .map(eventRepository::findById)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());


        return Compilation.builder()
                .pinned(newCompilationDto.getPinned())
                .title(newCompilationDto.getTitle())
                .events(events)
                .build();
    }

    public CompilationDto toDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(modelMapper.doListMapping(compilation.getEvents(), EventShortDto.class))
                .build();
    }
}
