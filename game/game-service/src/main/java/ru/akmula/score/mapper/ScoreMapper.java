package ru.akmula.score.mapper;

import ru.akmula.score.dto.NewScoreDto;
import ru.akmula.score.dto.ScoreDto;
import ru.akmula.score.entity.Score;

public class ScoreMapper {

    public static Score mapToScore(NewScoreDto dto) {
        return Score.builder()
                .level(dto.getLevel())
                .score(dto.getScore())
                .gameTime(dto.getGameTime())
                .created(dto.getCreated())
                .build();
    }

    public static ScoreDto mapToScoreDto(Score score) {
        return ScoreDto.builder()
                .level(score.getLevel())
                .score(score.getScore())
                .gameTime(score.getGameTime())
                .created(score.getCreated())
                .build();
    }
}
