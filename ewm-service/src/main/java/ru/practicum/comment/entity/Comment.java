package ru.practicum.comment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.common.enumiration.State;
import ru.practicum.event.entity.Event;
import ru.practicum.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.TIME_PATTERN;

@Entity
@Table(name = "comments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User owner;

    @Column(name = "event_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Event event;

    @Column(name = "created_on")
    @JsonFormat(pattern = TIME_PATTERN)
    private LocalDateTime createdOn;

    @Column(name = "published_on")
    @JsonFormat(pattern = TIME_PATTERN)
    private LocalDateTime publishedOn;

    @Column(name = "text")
    private String text;

    @Column(name = "is_positive")
    private Boolean isPositive;

    @Column(name = "state")
    private State state;
}