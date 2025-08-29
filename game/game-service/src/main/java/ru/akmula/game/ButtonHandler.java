package ru.akmula.game;

import lombok.extern.slf4j.Slf4j;
import ru.akmula.config.GameProperties;
import ru.akmula.game.enums.ButtonsName;
import ru.akmula.score.service.ScoreService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Slf4j
public record ButtonHandler(GameProperties gameProperties, ScoreService scoreService) implements ActionListener {
    static int level;

    public void actionPerformed(ActionEvent event) {

        ButtonsName buttonsName = ButtonsName.valueOf(event.getActionCommand());

        switch (buttonsName) {
            case START:
               // new Menu.LevelChange(gameProperties, scoreService).getLevel();
                break;
            case HELP:
                log.info(event.getActionCommand());
                break;
            case ABOUT:
                log.info(event.getActionCommand());
                break;
            case EXIT:
                System.exit(0);
                break;
            case START_GAME:
                new Game(level, gameProperties, scoreService).createField();

            default:
                log.error("Unhandled button event");
                break;
        }


    /*    if (buttonsName.equals(ButtonsName.START)) {
            System.out.println("startButton");
        } else if (buttonsName.equals(ButtonsName.CLOSE)) {
            System.out.println("closeButton");
        }*/
           /* MainFrame.main_panel.invalidate();
            MainFrame.main_panel.removeAll();
            SecondFrame frame = new SecondFrame();
            MainFrame.main_panel.add(frame);
            MainFrame.main_panel.revalidate();
            MainFrame.main_panel.repaint();*/
    }
}
