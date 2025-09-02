package ru.akmula.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.akmula.config.GameProperties;
import ru.akmula.game.enums.ButtonsName;
import ru.akmula.game.panel.AboutPanel;
import ru.akmula.game.panel.HelpPanel;
import ru.akmula.game.panel.ImagePanel;
import ru.akmula.game.service.HelpService;
import ru.akmula.score.service.ScoreService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ru.akmula.game.enums.ButtonsName.START_GAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class Menu implements ActionListener {

    private final GameProperties gameProperties;
    private final ScoreService scoreService;
    private final HelpService helpService;

    private JFrame menuFrame;
    private ImagePanel imagePanelMenu;
    private JButton closeButton;
    private int level = 4;

    public void start() {
        log.info("Создаем родительский фрейм!");
        menuFrame = new ParentFrame(gameProperties);

        imagePanelMenu = new ImagePanel(gameProperties.getImages().getBgMenu());

        log.info("Создаем панель для кнопок!");
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));

        log.info("Создаем кнопки!");
        JButton startButton = new JButton(new ImageIcon(gameProperties.getImages().getButtonStart()));
        JButton helpButton = new JButton(new ImageIcon(gameProperties.getImages().getButtonHelp()));
        JButton aboutButton = new JButton(new ImageIcon(gameProperties.getImages().getButtonAbout()));
        JButton exitButton = new JButton(new ImageIcon(gameProperties.getImages().getButtonExit()));
        closeButton = new JButton(new ImageIcon(gameProperties.getImages().getButtonExitLevel()));
        closeButton.setPreferredSize(new Dimension(102, 29));

        log.info("Добавляем кнопки на панель для кнопок!");
        buttonPanel.add(startButton);
        buttonPanel.add(helpButton);
        buttonPanel.add(aboutButton);
        buttonPanel.add(exitButton);

        log.info("Добавляем панель для кнопок на главную панель с фоном!");
        imagePanelMenu.add(buttonPanel).setBounds(65, 102, 169, 196);
        imagePanelMenu.setLayout(null);

        log.info("Добавляем в родительский фрейм главную панель с фоном!");
        menuFrame.add(imagePanelMenu);


        log.info("Добавляем обработчики!");
        startButton.setActionCommand(String.valueOf(ButtonsName.START));
        helpButton.setActionCommand(String.valueOf(ButtonsName.HELP));
        aboutButton.setActionCommand(String.valueOf(ButtonsName.ABOUT));
        exitButton.setActionCommand(String.valueOf(ButtonsName.EXIT));
        closeButton.setActionCommand(String.valueOf(ButtonsName.CLOSE));

        startButton.addActionListener(this);
        helpButton.addActionListener(this);
        aboutButton.addActionListener(this);
        exitButton.addActionListener(this);
        closeButton.addActionListener(this);

        log.info("Отображаем родительский фрейм!");
        menuFrame.setVisible(true);
    }

    private void setPanelToFrame(String title, JPanel jPanel) {
        menuFrame.getContentPane().removeAll();
        menuFrame.setTitle(title);
        menuFrame.getContentPane().add(jPanel);
        menuFrame.repaint();
        menuFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        ButtonsName buttonsName = ButtonsName.valueOf(event.getActionCommand());
        log.info("Нажата кнопка меню: {}", buttonsName);

        switch (buttonsName) {
            case START:
                new LevelChange();
                break;
            case HELP:
                JPanel helpPanel = new HelpPanel(helpService, gameProperties).getHelp();
                helpPanel.add(closeButton);
                setPanelToFrame(gameProperties.getTitleHelp(), helpPanel);
                break;
            case ABOUT:
                JPanel aboutPanel = new AboutPanel(gameProperties).getAbout();
                aboutPanel.add(closeButton);
                setPanelToFrame(gameProperties.getTitleAbout(), aboutPanel);
                break;
            case EXIT:
                System.exit(0);
                break;
            case CLOSE:
                setPanelToFrame(gameProperties.getTitle(), imagePanelMenu);
                break;
            }
    }

    private class LevelChange implements ActionListener {

        private final JDialog jdLevel;

        private LevelChange() {

            jdLevel = new JDialog();
            jdLevel.setModal(true);
            jdLevel.setResizable(false);
            jdLevel.setIconImage(Toolkit.getDefaultToolkit().getImage(gameProperties.getIcon()));

            ImagePanel imagePanelLevelChange = new ImagePanel(gameProperties.getImages().getBgAbout());

           imagePanelLevelChange.setLayout(null);

            JLabel jlLevel = new JLabel("Выберите сложность игры");

            JButton jbStartGame = new JButton(new ImageIcon(gameProperties.getImages().getButtonStartLevel()));
            JButton jbCancel = new JButton(new ImageIcon(gameProperties.getImages().getButtonExitLevel()));
            JRadioButton jrb3x3 = new JRadioButton("Поле 3x3");
            JRadioButton jrb4x4 = new JRadioButton("Поле 4x4", true);
            JRadioButton jrb5x5 = new JRadioButton("Поле 5x5");

            jrb3x3.setOpaque(false);
            jrb3x3.setFocusPainted(false);
            jrb4x4.setOpaque(false);
            jrb4x4.setFocusPainted(false);
            jrb5x5.setOpaque(false);
            jrb5x5.setFocusPainted(false);

            imagePanelLevelChange.add(jlLevel);
            imagePanelLevelChange.add(jbStartGame);
            imagePanelLevelChange.add(jbCancel);
            imagePanelLevelChange.add(jrb3x3);
            imagePanelLevelChange.add(jrb4x4);
            imagePanelLevelChange.add(jrb5x5);

            jlLevel.setHorizontalAlignment(SwingConstants.CENTER);

            ButtonGroup btLevel = new ButtonGroup();
            btLevel.add(jrb3x3);
            btLevel.add(jrb4x4);
            btLevel.add(jrb5x5);

            jdLevel.setBounds(600, 250, 200, 220);
            jlLevel.setBounds(10, 10, 170, 20);
            jrb3x3.setBounds(50, 40, 100, 10);
            jrb4x4.setBounds(50, 60, 100, 10);
            jrb5x5.setBounds(50, 80, 100, 10);
            jbStartGame.setBounds(45, 110, 102, 29);
            jbCancel.setBounds(45, 140, 102, 29);

           jdLevel.add(imagePanelLevelChange);

            jbStartGame.setActionCommand(String.valueOf(START_GAME));
            jbCancel.setActionCommand(String.valueOf(ButtonsName.CANCEL));
            jrb3x3.setActionCommand(String.valueOf(ButtonsName.LEVEL_3));
            jrb4x4.setActionCommand(String.valueOf(ButtonsName.LEVEL_4));
            jrb5x5.setActionCommand(String.valueOf(ButtonsName.LEVEL_5));

            jbStartGame.addActionListener(this);
            jbCancel.addActionListener(this);
            jrb3x3.addActionListener(this);
            jrb4x4.addActionListener(this);
            jrb5x5.addActionListener(this);

            jdLevel.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            ButtonsName buttonsName = ButtonsName.valueOf(event.getActionCommand());
            log.info("Нажата кнопка: {}", buttonsName);

            switch (buttonsName) {
                case START_GAME:
                    jdLevel.dispose();
                    menuFrame.dispose();
                    new Game(level, gameProperties, scoreService).createField();
                    break;
                case CANCEL:
                    jdLevel.dispose();
                    break;
                case LEVEL_3:
                    level = 3;
                    break;
                case LEVEL_4:
                    level = 4;
                    break;
                case LEVEL_5:
                    level = 5;
                    break;
            }
        }
    }
}