package ru.practicum.compilation.service;

import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {
    CompilationDto create(NewCompilationDto newCompilation);

    void delete(Integer id);

    CompilationDto edit(UpdateCompilationRequest updateCompilation, Integer compId);

    List<CompilationDto> findAll(Boolean pinned, Integer from, Integer size);

    CompilationDto findById(Integer id);
}
