package ru.practicum.category.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Builder
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 50, unique = true)
    private String name;
}
