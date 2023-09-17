package ru.practicum.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class NewUserRequest {
    @Email
    @Size(min = 6, max = 254)
    private String email;

    @NotNull
    @Size(min = 2, max = 250)
    private String name;
}