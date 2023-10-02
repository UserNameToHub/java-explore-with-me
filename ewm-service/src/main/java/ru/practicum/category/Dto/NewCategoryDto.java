package ru.practicum.category.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {
    @NotNull
    @Size(min = 1, max = 50, message = "Количество символов в названии должно быть в диапазоне от 1 до 50.")
    private String name;
}
