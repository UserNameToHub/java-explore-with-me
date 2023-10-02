package ru.practicum.category.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.category.Dto.CategoryDto;
import ru.practicum.category.entity.Category;
import ru.practicum.mapper.BaseMapper;

@Component
public class CategoryMapper implements BaseMapper<Category, CategoryDto> {
    @Override
    public Category toEntity(CategoryDto dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    @Override
    public CategoryDto toDto(Category entity) {
        return CategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}