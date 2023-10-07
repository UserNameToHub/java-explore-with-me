package ru.practicum.compilation.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
public class NewCompilationDto {
    private Set<Integer> events;
    private Boolean pinned;

    @NotBlank
    @Length(min = 1, max = 50)
    private String title;
}
