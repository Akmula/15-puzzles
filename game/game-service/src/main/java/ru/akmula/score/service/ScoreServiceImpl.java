package ru.akmula.score.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akmula.score.dto.NewScoreDto;
import ru.akmula.score.dto.ScoreDto;
import ru.akmula.score.entity.Score;
import ru.akmula.score.mapper.ScoreMapper;
import ru.akmula.score.repository.ScoreRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;

    @Override
    @Transactional
    public Score addScore(NewScoreDto dto) {
        log.info("Добавление записи о результатах игры в базу!");

        Score score = scoreRepository.save(ScoreMapper.mapToScore(dto));

        log.info("Добавлена запись: {}", score);
        return score;
    }

    @Override
    public ScoreDto getScoreByLevel(Long level) {

        List<Score> scores = scoreRepository.findAllByLevelOrderByScoreAsc(level);

        if (scores.isEmpty()) {
            return new ScoreDto(0L, 0L, "0", LocalDateTime.now());
        }

        return ScoreMapper.mapToScoreDto(scores.get(0));
    }
}
