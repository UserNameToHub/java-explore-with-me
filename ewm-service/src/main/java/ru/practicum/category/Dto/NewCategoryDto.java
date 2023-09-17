package ru.practicum.category.Dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@Builder
public class NewCategoryDto {
    @Size(min = 1, max = 50, message = "Количество символов в названии должно быть в диапазоне от 1 до 50.")
    private String name;
}
