package ru.practicum.category.service;

import ru.practicum.category.Dto.CategoryDto;
import ru.practicum.category.Dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll(Integer from, Integer size);

    CategoryDto findById(Integer id);

    CategoryDto create(NewCategoryDto newCategoryDto);

    void delete(Integer id);

    CategoryDto update(NewCategoryDto categoryDto, Integer id);
}

