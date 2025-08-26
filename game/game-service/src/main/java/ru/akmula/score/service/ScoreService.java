package ru.akmula.score.service;

import ru.akmula.score.dto.NewScoreDto;
import ru.akmula.score.dto.ScoreDto;

public interface ScoreService {

    void addScore(NewScoreDto dto);

    ScoreDto getScoreByLevel(Long level);

}