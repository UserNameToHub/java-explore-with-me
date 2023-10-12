package ru.practicum.compilation.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.entity.Compilation;
import ru.practicum.event.entity.Event;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public Compilation toEntity(NewCompilationDto newCompilationDto) {

        var events = Objects.nonNull(newCompilationDto.getEvents()) ? newCompilationDto.getEvents().stream()
                .map(eventRepository::findById)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList()) : new ArrayList<Event>();


        return Compilation.builder()
                .pinned(Objects.nonNull(newCompilationDto.getPinned()) ? newCompilationDto.getPinned() : false)
                .title(newCompilationDto.getTitle())
                .events(events)
                .build();
    }

    public CompilationDto toDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(eventMapper.toShortDtoList(compilation.getEvents()))
                .build();
    }

    public List<CompilationDto> toListDto(List<Compilation> compilations) {
        return compilations.stream().map(this::toDto).collect(Collectors.toList());
    }
}
