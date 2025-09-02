package ru.akmula.game.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.akmula.game.entity.Help;
import ru.akmula.game.repository.GameRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HelpServiceImpl implements HelpService {
    private final GameRepository gameRepository;

    @Override
    public Help findHelp() {

        Optional<Help> help = gameRepository.findById(1);

        return help.orElse(new Help(0L, "Не удалось загрузить описание!"));
    }
}