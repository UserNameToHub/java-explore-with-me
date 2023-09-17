package ru.practicum.compilation.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class UpdateCompilationRequest {
    private List<Integer> events;
    private Boolean pinned;

    @Size(min = 1, max = 50)
    private String title;
}
