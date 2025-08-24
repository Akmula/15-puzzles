package ru.akmula.score.service;

import ru.akmula.score.dto.NewScoreDto;
import ru.akmula.score.dto.ScoreDto;
import ru.akmula.score.entity.Score;

public interface ScoreService {

    Score addScore(NewScoreDto dto);

    ScoreDto getScoreByLevel(Long level);

}