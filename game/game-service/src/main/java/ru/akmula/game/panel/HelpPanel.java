package ru.akmula.game.panel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.akmula.config.GameProperties;
import ru.akmula.game.entity.Help;
import ru.akmula.game.service.HelpService;

import javax.swing.*;

@Component
@RequiredArgsConstructor
public class HelpPanel extends JPanel {

    private final HelpService helpService;
    private final GameProperties gameProperties;

    public JPanel getHelp() {

        ImagePanel imagePanel = new ImagePanel(gameProperties.getImages().getBgAbout());

        Help help = helpService.findHelp();

        JLabel labelHelp = new JLabel(help.getHelpText());
        labelHelp.setHorizontalTextPosition(JLabel.CENTER);
        labelHelp.setVerticalTextPosition(JLabel.TOP);

        imagePanel.add(labelHelp);

        return imagePanel;
    }
}