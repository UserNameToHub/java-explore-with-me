package ru.practicum.category.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.category.Dto.CategoryDto;
import ru.practicum.category.Dto.NewCategoryDto;
import ru.practicum.category.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDto> findAll(Integer from, Integer size);

    CategoryDto findById(Integer id);

    CategoryDto create(NewCategoryDto newCategoryDto);

    void delete(Integer id);

    CategoryDto update(NewCategoryDto categoryDto, Integer id);
}

