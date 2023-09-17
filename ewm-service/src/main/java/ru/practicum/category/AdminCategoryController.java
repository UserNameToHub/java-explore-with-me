package ru.practicum.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.Dto.CategoryDto;
import ru.practicum.category.Dto.NewCategoryDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(NewCategoryDto categoryDto) {
        return  null;
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("catId") Integer catId) {

    }

    @PatchMapping("/{catId")
    public CategoryDto edit(@RequestBody @Valid NewCategoryDto categoryDto,
                            @PathVariable("catId") Integer catId) {
        return null;
    }
}