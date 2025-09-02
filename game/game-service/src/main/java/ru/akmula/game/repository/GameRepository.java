package ru.akmula.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.akmula.game.entity.Help;

public interface GameRepository extends JpaRepository<Help, Integer> {
}