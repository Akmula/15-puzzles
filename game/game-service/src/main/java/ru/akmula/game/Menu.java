package ru.akmula.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.akmula.config.GameProperties;
import ru.akmula.game.service.HelpService;
import ru.akmula.score.service.ScoreService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class Menu {

    private final GameProperties gameProperties;
    private final ScoreService scoreService;
    private final HelpService helpService;

    JFrame menuFrame;
    ImagePanel imagePanelMenu;
    JPanel buttonPanel;
    JButton startButton;
    JButton aboutButton;
    JButton helpButton;
    JButton exitButton;
    JButton closeButton;
    int level = 4;

    public void start() {
        log.info("Создаем фрейм!");
        // ---------- Окно меню
        menuFrame = new ParentFrame(gameProperties);

        imagePanelMenu = new ImagePanel(gameProperties.getImages().getBgMenu());

        buttonPanel = new JPanel(new GridLayout(4, 1));
        buttonPanel.add(startButton = new JButton(new ImageIcon(gameProperties.getImages().getButtonStart())));
        buttonPanel.add(helpButton = new JButton(new ImageIcon(gameProperties.getImages().getButtonHelp())));
        buttonPanel.add(aboutButton = new JButton(new ImageIcon(gameProperties.getImages().getButtonAbout())));
        buttonPanel.add(exitButton = new JButton(new ImageIcon(gameProperties.getImages().getButtonExit())));
        imagePanelMenu.add(buttonPanel).setBounds(65, 102, 169, 196);
        imagePanelMenu.setLayout(null);
        menuFrame.add(imagePanelMenu);
        menuFrame.setVisible(true);

        // ---------- Вешаем обработчики
        closeButton = new JButton(new ImageIcon(gameProperties.getImages().getButtonExitLevel()));
        closeButton.setPreferredSize(new Dimension(102, 29));
       // closeButton.setBounds(65, 29, 169, 29);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.getContentPane().removeAll();
                menuFrame.setTitle(gameProperties.getTitle());
                menuFrame.getContentPane().add(imagePanelMenu);
                menuFrame.repaint();
                menuFrame.setVisible(true);
            }
        });

        // ---------- Старт
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LevelChange();
            }
        });

        // ---------- Помощь
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel helpPanel = new GameHelp(helpService, gameProperties).getHelp();
                helpPanel.add(closeButton);

                menuFrame.getContentPane().removeAll();
                menuFrame.setTitle(gameProperties.getTitleHelp());
                menuFrame.getContentPane().add(helpPanel);
                menuFrame.repaint();
                menuFrame.setVisible(true);
            }
        });

        // ---------- О программе
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel aboutPanel = new GameAbout(gameProperties).getAbout();
                aboutPanel.add(closeButton, BorderLayout.EAST);
                menuFrame.getContentPane().removeAll();
                menuFrame.setTitle(gameProperties.getTitleAbout());
                menuFrame.getContentPane().add(aboutPanel);
                menuFrame.repaint();
                menuFrame.setVisible(true);
            }
        });

        // ---------- Выход
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private class LevelChange implements ActionListener {
        JDialog jdLevel;
        JLabel jlLevel;
        ImagePanel imagePanelLevelChange;
        JButton jbStartGame;
        JButton jbCancel;
        JRadioButton jrb3x3;
        JRadioButton jrb4x4;
        JRadioButton jrb5x5;

       private LevelChange() {
            jdLevel = new JDialog();
            jdLevel.setModal(true);
            jdLevel.setResizable(false);
            jdLevel.setIconImage(Toolkit.getDefaultToolkit().getImage(gameProperties.getIcon()));

           imagePanelLevelChange = new ImagePanel(gameProperties.getImages().getBgAbout());

           imagePanelLevelChange.setLayout(null);
           imagePanelLevelChange.add(jlLevel = new JLabel("Выберите сложность игры"));
           imagePanelLevelChange.add(jbStartGame = new JButton(new ImageIcon(gameProperties.getImages().getButtonStartLevel())));
           imagePanelLevelChange.add(jbCancel = new JButton(new ImageIcon(gameProperties.getImages().getButtonExitLevel())));
           imagePanelLevelChange.add(jrb3x3 = new JRadioButton("Поле 3x3"));
           imagePanelLevelChange.add(jrb4x4 = new JRadioButton("Поле 4x4", true));
           imagePanelLevelChange.add(jrb5x5 = new JRadioButton("Поле  5x5"));
            jlLevel.setHorizontalAlignment(SwingConstants.CENTER);
            jrb3x3.setOpaque(false);
            jrb3x3.setFocusPainted(false);
            jrb4x4.setOpaque(false);
            jrb4x4.setFocusPainted(false);
            jrb5x5.setOpaque(false);
            jrb5x5.setFocusPainted(false);
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
            jrb3x3.addActionListener(this);
            jrb4x4.addActionListener(this);
            jrb5x5.addActionListener(this);

            jbStartGame.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jdLevel.dispose();
                    menuFrame.dispose();
                    new Game(level, gameProperties, scoreService).createField();
                }
            });
            jbCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jdLevel.dispose();
                }
            });

            jdLevel.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (jrb3x3.isSelected()) level = 3;
            else if (jrb4x4.isSelected()) level = 4;
            else level = 5;
        }
    }
}