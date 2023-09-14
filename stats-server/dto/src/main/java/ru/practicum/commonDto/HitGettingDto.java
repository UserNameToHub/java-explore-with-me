package ru.practicum.commonDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HitGettingDto {
    private String app;
    private String uri;
    private Long hits;
}
