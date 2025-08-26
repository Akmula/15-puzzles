package ru.akmula.score.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.akmula.score.entity.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    Score findFirstByLevelOrderByScoreAsc(Long level);

}