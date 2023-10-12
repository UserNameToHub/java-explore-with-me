package ru.practicum.category.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.category.Dto.NewCategoryDto;
import ru.practicum.category.entity.Category;
import ru.practicum.mapper.BaseMapper;

@Component
public class NewCategoryMapper implements BaseMapper<Category, NewCategoryDto> {
    @Override
    public Category toEntity(NewCategoryDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    @Override
    public NewCategoryDto toDto(Category entity) {
        return NewCategoryDto.builder()
                .name(entity.getName())
                .build();
    }
}