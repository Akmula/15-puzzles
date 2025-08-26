package ru.akmula.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.akmula.config.GameProperties;
import ru.akmula.score.service.ScoreService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class Menu {

    private final GameProperties gameProperties;
    private final ScoreService scoreService;

    JFrame MenuFrame;
    ImagePanel ipMenu;
    JPanel buttonPanel;
    JButton startButton;
    JButton aboutButton;
    JButton helpButton;
    JButton exitButton;
    int level = 4;

    public void init() {
        log.info("Создаем фрейм!");
        // ---------- Окно меню
        MenuFrame = new JFrame(gameProperties.getTitle());
        MenuFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(gameProperties.getIcon()));
        MenuFrame.setBounds(550, 150, 300, 450);
        MenuFrame.setResizable(false);
        MenuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ipMenu = new ImagePanel();

        try {
            ipMenu.setImage(ImageIO.read(new File(gameProperties.getImages().getBgMenu())));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        buttonPanel = new JPanel(new GridLayout(4, 1));
        buttonPanel.add(startButton = new JButton(new ImageIcon(gameProperties.getImages().getButtonStart())));
        buttonPanel.add(helpButton = new JButton(new ImageIcon(gameProperties.getImages().getButtonHelp())));
        buttonPanel.add(aboutButton = new JButton(new ImageIcon(gameProperties.getImages().getButtonAbout())));
        buttonPanel.add(exitButton = new JButton(new ImageIcon(gameProperties.getImages().getButtonExit())));
        ipMenu.add(buttonPanel).setBounds(65, 102, 169, 196);
        ipMenu.setLayout(null);
        MenuFrame.add(ipMenu);
        MenuFrame.setVisible(true);

        // ---------- Вешаем обработчики
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
                Help();
            }
        });

        // ---------- О программе
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                About();
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

    // --------- О программе ---------- //
    public void About() {
        String txt = "<html><center><H2>О программе</H2><br>" +
                "v.1.00</center></html>";
        JFrame AboutFrame = new JFrame("О программе");
        JLabel jlImage = new JLabel(new ImageIcon(gameProperties.getLogo()));
        JLabel jlAbout = new JLabel(txt);
        jlAbout.setHorizontalAlignment(SwingConstants.CENTER);
        jlAbout.setVerticalAlignment(SwingConstants.TOP);
        AboutFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(gameProperties.getIcon()));
        AboutFrame.setBounds(550, 150, 300, 450);
        AboutFrame.setLayout(new GridLayout(2, 1));
        AboutFrame.add(jlImage);
        AboutFrame.add(jlAbout);
        AboutFrame.setVisible(true);
    }

    // --------- Помощь ---------- //
    public void Help() {
        String txt = "<html><center><H2>Помощь</H2><br>" +
                "Цель игры — выстроить или переместить костяшки слева на право по возрастанию в коробке и тем самым " +
                "добиться упорядочивания их по номерам. При этом ставится дополнительная задача - сделать как можно " +
                "меньше ходов.<br>" +
                "Для перемещения костяшки нажмите на ту, которую необходимо переместить, и она автоматически " +
                "переместится.</center></html>";
        JFrame HelpFrame = new JFrame("Справка");
        JLabel jlHelp = new JLabel(txt);
        jlHelp.setHorizontalAlignment(SwingConstants.CENTER);
        jlHelp.setVerticalAlignment(SwingConstants.TOP);
        HelpFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(gameProperties.getIcon()));
        HelpFrame.setBounds(550, 150, 300, 450);
        HelpFrame.add(jlHelp);
        HelpFrame.setVisible(true);
    }

    public class LevelChange implements ActionListener {
        JDialog jdLevel;
        JLabel jlLevel;
        ImagePanel ipLevel;
        JButton jbStartGame;
        JButton jbCancel;
        JRadioButton jrb3x3;
        JRadioButton jrb4x4;
        JRadioButton jrb5x5;

        LevelChange() {
            jdLevel = new JDialog();
            jdLevel.setModal(true);
            jdLevel.setResizable(false);
            jdLevel.setIconImage(Toolkit.getDefaultToolkit().getImage(gameProperties.getIcon()));
            ipLevel = new ImagePanel();
            try {
                ipLevel.setImage(ImageIO.read(new File(gameProperties.getImages().getBgAbout())));
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            ipLevel.setLayout(null);
            ipLevel.add(jlLevel = new JLabel("Выберите сложность игры"));
            ipLevel.add(jbStartGame = new JButton(new ImageIcon(gameProperties.getImages().getButtonStartLevel())));
            ipLevel.add(jbCancel = new JButton(new ImageIcon(gameProperties.getImages().getButtonExitLevel())));
            ipLevel.add(jrb3x3 = new JRadioButton("Поле 3x3"));
            ipLevel.add(jrb4x4 = new JRadioButton("Поле 4x4", true));
            ipLevel.add(jrb5x5 = new JRadioButton("Поле  5x5"));
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
            jdLevel.add(ipLevel);
            jrb3x3.addActionListener(this);
            jrb4x4.addActionListener(this);
            jrb5x5.addActionListener(this);

            jbStartGame.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jdLevel.dispose();
                    MenuFrame.dispose();
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