package ru.akmula.score.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.akmula.score.entity.Score;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    List<Score> findAllByLevelOrderByScoreAsc(Long level);

}