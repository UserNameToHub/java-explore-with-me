package ru.practicum.compilation.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class UpdateCompilationRequest {
    private Set<Integer> events;
    private Boolean pinned;

    @Size(min = 1, max = 50)
    private String title;
}
