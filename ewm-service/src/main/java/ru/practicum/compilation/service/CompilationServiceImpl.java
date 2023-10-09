package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.util.ReflectionChange;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.entity.Compilation;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.entity.Event;
import ru.practicum.event.repository.EventRepository;

import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.util.Constants.ORDER_BY_ID_ASC;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;

    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto newCompilation) {
        log.info("Creating compilation with pinned {} and title {}", newCompilation.getPinned(), newCompilation.getTitle());
        return compilationMapper.toDto(compilationRepository.save(compilationMapper.toEntity(newCompilation)));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        log.info("Deleting compilation with id {}", id);
        compilationRepository.delete(compilationRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Compilation with id=%d was not found", id))));
    }

    @Override
    @Transactional
    public CompilationDto edit(UpdateCompilationRequest updateCompilation, Integer compId) {
        log.info("Editing compilation with id {}", compId);
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() ->
                new NotFoundException(String.format("Compilation with id=%d was not found", compId)));

        var events = Objects.nonNull(updateCompilation.getEvents()) ?
                updateCompilation.getEvents().stream()
                        .map(eventRepository::findById)
                        .map(Optional::get)
                        .collect(Collectors.toList()) : new ArrayList<Event>();

        ReflectionChange.go(compilation, updateCompilation);
        compilation.setEvents(events);

        return compilationMapper.toDto(compilationRepository.save(compilation));
    }

    @Override
    public List<CompilationDto> findAll(Boolean pinned, Integer from, Integer size) {
        log.info("Get all compilation");
        return compilationMapper.toListDto(compilationRepository.findAll(pinned,
                PageRequest.of(from, size, ORDER_BY_ID_ASC)).toList());
    }

    @Override
    public CompilationDto findById(Integer id) {
        log.info("Get compilation with id {}", id);
        Compilation compilation = compilationRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Compilation with id=% was not found", id)));

        return compilationMapper.toDto(compilation);
    }
}