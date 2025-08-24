package ru.akmula.score.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDto {
    Long level;
    Long score;
    String gameTime;
    LocalDateTime created;
}