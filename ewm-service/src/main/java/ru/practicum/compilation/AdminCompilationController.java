package ru.practicum.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    private final CompilationService compilationService;

    @PostMapping
    public CompilationDto create(@RequestBody NewCompilationDto compilationDto) {
        return compilationService.create(compilationDto);
    }

    @PostMapping("/{compId}")
    public void delete(@PathVariable("compId") Integer compId) {
        compilationService.delete(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto edit(@RequestBody @Valid UpdateCompilationRequest updateCompilation,
                               @PathVariable("compId") Integer compId) {
        return compilationService.edit(updateCompilation, compId);
    }
}