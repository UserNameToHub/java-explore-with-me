package ru.practicum.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.common.enumiration.State;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.TIME_PATTERN;

@Data
@Builder
public class ParticipationRequestDto {
    @NotNull
    @JsonFormat(pattern = TIME_PATTERN)
    private LocalDateTime created;

    @NotNull
    private Integer event;

    @NotNull
    private Integer id;

    @NotNull
    private Integer requester;

    @NotNull
    private State status;
}