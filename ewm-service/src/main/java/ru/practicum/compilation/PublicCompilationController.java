package ru.practicum.compilation;


import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;

@RestController
@RequestMapping("compilations")
public class PublicCompilationController {
    @GetMapping
    public CompilationDto getAll(@RequestParam(value = "pinned", required = false) Boolean pinned,
                                 @RequestParam(value = "from", defaultValue = "0") Integer from,
                                 @RequestParam(value = "size", defaultValue = "10") Integer sizq) {
        return null;
    }

    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable("compId") Integer compId) {
        return null;
    }
}
