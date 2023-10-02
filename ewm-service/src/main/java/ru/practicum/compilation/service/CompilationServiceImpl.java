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
import ru.practicum.mapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    private final ModelMapper modelMapper;


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

        var events = updateCompilation.getEvents().stream()
                .map(eventRepository::findById)
                .map(Optional::get)
                .collect(Collectors.toList());

        ReflectionChange.go(compilation, updateCompilation);
        compilation.setEvents(events);
//        compilation.setPinned(updateCompilation.getPinned());
//        compilation.setTitle(updateCompilation.getTitle());

        return compilationMapper.toDto(compilationRepository.save(compilation));
    }

    @Override
    public List<CompilationDto> findAll(Boolean pinned, Integer from, Integer size) {
        log.info("Get all compilation");
        return modelMapper.doListMapping(compilationRepository.findAll(pinned,
                PageRequest.of(from, size, ORDER_BY_ID_ASC)).toList(), CompilationDto.class);
    }

    @Override
    public CompilationDto findById(Integer id) {
        log.info("Get compilation with id {}", id);
        Compilation compilation = compilationRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Compilation with id=% was not found", id)));

        return modelMapper.doMapping(compilation, CompilationDto.builder().build());
    }
}