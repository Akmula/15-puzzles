package ru.akmula.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.akmula.config.GameProperties;
import ru.akmula.game.entity.Help;
import ru.akmula.game.service.HelpService;

import javax.swing.*;

@Component
@RequiredArgsConstructor
public class GameHelp extends JPanel {

    private final HelpService helpService;
    private final GameProperties gameProperties;
    ImagePanel imagePanel;
    JLabel lbHelp;
    Help help;

    public JPanel getHelp() {

        imagePanel = new ImagePanel(gameProperties.getImages().getBgAbout());

        help = helpService.findHelp();

        lbHelp = new JLabel(help.getHelpText());
        lbHelp.setHorizontalTextPosition(JLabel.CENTER);
        lbHelp.setVerticalTextPosition(JLabel.TOP);

        imagePanel.add(lbHelp);

        return imagePanel;
    }
}