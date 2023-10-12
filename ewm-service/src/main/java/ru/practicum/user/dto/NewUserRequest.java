package ru.practicum.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class NewUserRequest {
    @Email
    @NotNull
    @Size(min = 6, max = 254, message = "Количество символов в названии должно быть в диапазоне от 6 до 254.")
    private String email;

    @NotBlank
    @Size(min = 2, max = 250, message = "Количество символов в названии должно быть в диапазоне от 2 до 250.")
    private String name;
}