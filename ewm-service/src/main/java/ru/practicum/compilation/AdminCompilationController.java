package ru.practicum.compilation;

import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/compilations")
public class AdminCompilationController {
    @PostMapping
    public List<CompilationDto> create(@RequestBody @Valid NewCompilationDto compilationDto) {
        return null;
    }

    @PostMapping("/{compId}")
    public void delete(@PathVariable("compId") Integer compId) {

    }

    @PatchMapping("/{compId}")
    public List<CompilationDto> edit(@RequestBody @Valid UpdateCompilationRequest updateCompilation,
                                     @PathVariable("compId") Integer compId) {
        return null;
    }
}
