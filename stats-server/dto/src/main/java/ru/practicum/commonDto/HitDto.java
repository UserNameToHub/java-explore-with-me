package ru.practicum.commonDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.util.Constatns.TIME_PATTERN;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HitDto {
    private Long id;

    @NotBlank(message = "Данное поле не может быть пустым.")
    String app;

    @NotBlank(message = "Данное поле не может быть пустым.")
    String uri;

    @NotBlank(message = "Данное поле не может быть пустым.")
    String ip;

    @NotNull(message = "Данное поле не может быть пустым.")
    @JsonFormat(pattern = TIME_PATTERN)
    LocalDateTime timestamp;
}
