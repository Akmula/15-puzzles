package ru.akmula.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.akmula.config.GameProperties;

import javax.swing.*;
import java.awt.*;

@Component
@RequiredArgsConstructor
public class GameAbout extends JPanel {

    private final GameProperties gameProperties;
    ImagePanel imagePanel;

    public JPanel getAbout() {

        String txt = "<html>" +
                     "<center>" +
                     "<H2>О программе</H2><br>" +
                     "Версия: " + gameProperties.getVersion() + "<br>" +
                     "</center> " +
                     "</html>";

        imagePanel = new ImagePanel(gameProperties.getImages().getBgAbout());

        JLabel jlImage = new JLabel(new ImageIcon(gameProperties.getLogo()));
        JLabel jlAbout = new JLabel(txt);

        jlAbout.setHorizontalAlignment(SwingConstants.CENTER);
        jlAbout.setVerticalAlignment(SwingConstants.TOP);
        jlAbout.setPreferredSize(new Dimension(400, 120));

        imagePanel.add(jlImage);
        imagePanel.add(jlAbout);

        return imagePanel;
    }
}