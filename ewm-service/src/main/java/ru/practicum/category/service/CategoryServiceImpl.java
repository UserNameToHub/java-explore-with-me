package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.Dto.CategoryDto;
import ru.practicum.category.Dto.NewCategoryDto;
import ru.practicum.category.entity.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.common.exception.ConflictException;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.mapper.ModelMapper;

import java.util.List;

import static ru.practicum.util.Constants.ORDER_BY_ID_ASC;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDto> findAll(Integer from, Integer size) {
        log.info("Get all categories.");
        return modelMapper.doListMapping(categoryRepository.findAll(PageRequest.of(from, size, ORDER_BY_ID_ASC)).toList(),
                CategoryDto.class);
    }

    @Override
    public CategoryDto findById(Integer id) {
        log.info("Get user with id {}.", id);
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new RuntimeException(String.format("Category with id=%d was not found", id)));

        return modelMapper.doMapping(category, CategoryDto.builder().build());
    }

    @Override
    @Transactional
    public CategoryDto create(NewCategoryDto newCategoryDto) {
        log.info("Creating category with name {}", newCategoryDto.getName());
        return modelMapper.doMapping(categoryRepository.saveAndFlush(modelMapper.doMapping(newCategoryDto, new Category())),
                new CategoryDto());
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        log.info("Delete category with id {}.", id);
        if (!categoryRepository.existsById(id)) {
            log.warn("Category with id={} was not found", id);
            throw new NotFoundException(String.format("Category with id=%d was not found", id));
        }

        if (eventRepository.existsEventByCategoryId(id)) {
            log.warn("Category with id={} is not empty", id);
            throw new ConflictException("The category is not empty");
        }

        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CategoryDto update(NewCategoryDto categoryDto, Integer id) {
        log.info("Updating category with id={}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", id)));
        category.setName(categoryDto.getName());

        return modelMapper.doMapping(categoryRepository.saveAndFlush(category), new CategoryDto());
    }
}