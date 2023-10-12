package ru.practicum.compilation.service;

import org.springframework.lang.Nullable;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {
    CompilationDto create(@Nullable NewCompilationDto newCompilation);

    void delete(Integer id);

    CompilationDto edit(UpdateCompilationRequest updateCompilation, Integer compId);

    List<CompilationDto> findAll(Boolean pinned, Integer from, Integer size);

    CompilationDto findById(Integer id);
}
