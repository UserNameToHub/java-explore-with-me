package ru.practicum.stats.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "endpoint_hit")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
