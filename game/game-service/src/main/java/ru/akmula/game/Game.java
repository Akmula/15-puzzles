package ru.akmula.game;

import lombok.extern.slf4j.Slf4j;
import ru.akmula.config.GameProperties;
import ru.akmula.score.dto.NewScoreDto;
import ru.akmula.score.dto.ScoreDto;
import ru.akmula.score.service.ScoreService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;

@Slf4j
public class Game implements ActionListener {

    private ScoreService scoreService;
    JFrame GameFrame;
    ImagePanel ipGame;
    JButton jbNewGame;
    JButton jbExit;
    JButton jbClick;
    JButton pressButton;
    JPanel GameField;
    JButton[][] jbArray;
    Font bFont;
    int buttonClick;
    int n;

    Instant start;

// Здесь должен быть ваш код

    Instant end;

    Game(int n, GameProperties gameProperties, ScoreService scoreService) {
        this.scoreService = scoreService;
        // ---------- Окно игры
        GameFrame = new JFrame(gameProperties.getTitle());
        GameFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(gameProperties.getIcon()));
        GameFrame.setBounds(550, 150, 300, 450);
        GameFrame.setResizable(false);
        GameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ipGame = new ImagePanel();
        try {
            ipGame.setImage(ImageIO.read(new File(gameProperties.getImages().getBgMenu())));
        } catch (IOException e) {
            log.error(e.getMessage());
          //  e.printStackTrace();
        }
        ipGame.add(GameField = new JPanel());
        ipGame.setLayout(null);
        ipGame.add(jbNewGame = new JButton(new ImageIcon(gameProperties.getImages().getButtonNewGame())));
        ipGame.add(jbExit = new JButton(new ImageIcon(gameProperties.getImages().getButtonNewGameExit())));
        ipGame.add(jbClick = new JButton(gameProperties.getTitle()));
        GameField.setBounds(25, 25, 250, 250);
        jbClick.setBounds(25, 280, 250, 35);
        jbNewGame.setBounds(25, 325, 120, 33);
        jbExit.setBounds(155, 325, 120, 33);
        jbClick.setEnabled(false);
        GameFrame.add(ipGame);
        GameFrame.setVisible(true);
        bFont = new Font("Verdana", Font.BOLD, 16 - n);

        // ---------- Создаем игровое поле
        jbArray = new JButton[n][n];
        this.n = n;
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++) {
                jbArray[row][col] = new JButton();
                jbArray[row][col].setFont(bFont);
                jbArray[row][col].setFocusable(false);
                jbArray[row][col].addActionListener(this);
                GameField.add(jbArray[row][col]);
            }
        // ---------- Вешаем обработчики
        jbNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createField();
            }
        });
        jbExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  new Menu();
                GameFrame.dispose();
            }
        });
    }

    // ---------- Создание поля
    public void createField() {
        int[] field = new int[n * n];
        LinkedList<String> ll = new LinkedList<>();
        GameField.setLayout(new GridLayout(n, n));
        buttonClick = 0;
        jbClick.setText("Вы не сделали хода");
        for (int i = 1; i < field.length; i++) {
            ll.add(String.valueOf(i));
        }
        ll.add("");
        Collections.shuffle(ll);
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++) {
                jbArray[row][col].setText(String.valueOf(ll.poll()));
            }
        start = Instant.now();
    }

    // ---------- Условие победы ---------- //
    public boolean victory() {
        int count = 1;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                String victoryText = String.valueOf(count);
                if (!jbArray[row][col].getText().equals(victoryText) && count < n * n)
                    return false;
                count++;
            }
        }
        return true;
    }

    public void setText() {
        String txt;
        if (buttonClick == 0) {
            txt = "ход";
        } else if (buttonClick == 1 || buttonClick == 2 || buttonClick == 3) {
            txt = "хода";
        } else {
            txt = "ходов";
        }
        jbClick.setText("Вы сделали " + String.valueOf(buttonClick + 1) + " " + txt + "!");
    }

    public void congratulations(String recordData, int recordClick) {
        String recordText;
        if (buttonClick < recordClick) {
            recordText = "Вы установили рекорд!";
        } else recordText = "Рекорд " + recordClick + " ходов!<br>" +
                recordData;
        String txt = "<html><center><H2>Поздравляем!</H2><br>" +
                "Вы выиграли!<br>" +
                "Вы сделали: " + buttonClick + " ходов!<br>"
                + recordText + ". <br>" +
                "Хотите еще сыграть?</center></html>";
        final JDialog EndGame = new JDialog(GameFrame, true);
        JButton jbYes = new JButton("Да");
        JButton jbNo = new JButton("Нет");
        JLabel jlEndGame = new JLabel(txt);
        jlEndGame.setHorizontalAlignment(SwingConstants.CENTER);
        EndGame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        EndGame.setBounds(550, 250, 300, 250);
        EndGame.setLayout(null);
        EndGame.add(jlEndGame);
        EndGame.add(jbYes);
        EndGame.add(jbNo);
        jlEndGame.setBounds(10, 10, 270, 160);
        jbYes.setBounds(10, 180, 120, 30);
        jbNo.setBounds(160, 180, 120, 30);
        jbYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createField();
                EndGame.dispose();
            }
        });
        jbNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // new Menu();
                EndGame.dispose();
                GameFrame.dispose();
            }
        });

        EndGame.setVisible(true);
    }

    // ---------- Ход игры --------- //
    private boolean isEmptyButton(int row1, int col1) {
        if (row1 < 0 || row1 >= n)
            return false;
        if (col1 < 0 || col1 >= n)
            return false;
        return jbArray[row1][col1].getText().equals("");
    }

    private void setTextB(int row1, int col1) {
        jbArray[row1][col1].setText(pressButton.getText());
        pressButton.setText("");
        buttonClick++;
    }

    public void actionPerformed(ActionEvent e) {
        pressButton = (JButton) e.getSource();
        for (int row = 0; row < jbArray.length; row++)
            for (int col = 0; col < jbArray[row].length; col++) {
                if (pressButton == jbArray[row][col]) {
                    setText();
                    if (isEmptyButton(row - 1, col)) {
                        setTextB(row - 1, col);
                    } else if (isEmptyButton(row + 1, col)) {
                        setTextB(row + 1, col);
                    } else if (isEmptyButton(row, col - 1)) {
                        setTextB(row, col - 1);
                    } else if (isEmptyButton(row, col + 1)) {
                        setTextB(row, col + 1);
                    }
                    if (victory()) {
                        end = Instant.now();
                        saveScore(buttonClick);
                    }
                    return;
                }
            }
    }

    public void saveScore(int buttonClick) {
        ScoreDto scoreDto = scoreService.getScoreByLevel((long) n);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss:ms");
        Duration timeElapsed = Duration.between(start, end);
        LocalDateTime dateTime = LocalDateTime.now();
        NewScoreDto dto = NewScoreDto.builder()
                .level((long) n)
                .score((long) buttonClick)
                .gameTime(LocalTime.ofSecondOfDay(timeElapsed.getSeconds()).format(formatter))
                .created(dateTime)
                .build();
        scoreService.addScore(dto);

        congratulations(scoreDto.getScore().toString(), buttonClick);

    }
}