package ru.akmula.score.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scores")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "level", nullable = false)
    Long level;

    @Column(name = "score", nullable = false)
    Long score;

    @Column(name = "game_time", nullable = false)
    String gameTime;

    @Column(name = "created", nullable = false)
    LocalDateTime created;

}