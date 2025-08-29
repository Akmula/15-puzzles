package ru.akmula.game;

import lombok.extern.slf4j.Slf4j;
import ru.akmula.config.GameProperties;
import ru.akmula.score.dto.NewScoreDto;
import ru.akmula.score.dto.ScoreDto;
import ru.akmula.score.service.ScoreService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;

@Slf4j
public class Game implements ActionListener {

    private final ScoreService scoreService;
    JFrame GameFrame;
    ImagePanel backgroundGame;
    JButton newGameButton;
    JButton exitButton;
    JButton clickButton;
    JButton pressButton;
    JPanel GameField;
    JButton[][] buttonsArray;
    Font font;
    int buttonClick;
    int level;

    Instant startGame;
    Instant endGame;

    Game(int level, GameProperties gameProperties, ScoreService scoreService) {
        this.scoreService = scoreService;
        // ---------- Окно игры
        GameFrame = new JFrame(gameProperties.getTitle());
        GameFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(gameProperties.getIcon()));
        GameFrame.setBounds(550, 150, 300, 450);
        GameFrame.setResizable(false);
        GameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        backgroundGame = new Background(gameProperties.getImages().getBgMenu()).getImagePanel();

        backgroundGame.add(GameField = new JPanel());
        backgroundGame.setLayout(null);
        backgroundGame.add(newGameButton = new JButton(new ImageIcon(gameProperties.getImages().getButtonNewGame())));
        backgroundGame.add(exitButton = new JButton(new ImageIcon(gameProperties.getImages().getButtonNewGameExit())));
        backgroundGame.add(clickButton = new JButton(gameProperties.getTitle()));
        GameField.setBounds(25, 25, 250, 250);
        clickButton.setBounds(25, 280, 250, 35);
        newGameButton.setBounds(25, 325, 120, 33);
        exitButton.setBounds(155, 325, 120, 33);
        clickButton.setEnabled(false);
        GameFrame.add(backgroundGame);
        GameFrame.setVisible(true);
        font = new Font("Verdana", Font.BOLD, 16 - level);

        // ---------- Создаем игровое поле
        buttonsArray = new JButton[level][level];
        this.level = level;
        for (int row = 0; row < level; row++)
            for (int col = 0; col < level; col++) {
                buttonsArray[row][col] = new JButton();
                buttonsArray[row][col].setFont(font);
                buttonsArray[row][col].setFocusable(false);
                buttonsArray[row][col].addActionListener(this);
                GameField.add(buttonsArray[row][col]);
            }
        // ---------- Вешаем обработчики
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createField();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  new Menu();
                GameFrame.dispose();
            }
        });
    }

    // ---------- Создание поля
    public void createField() {
        int[] field = new int[level * level];
        LinkedList<String> ll = new LinkedList<>();
        GameField.setLayout(new GridLayout(level, level));
        buttonClick = 1;
        clickButton.setText("Вы не сделали хода");
        for (int i = 1; i < field.length; i++) {
            ll.add(String.valueOf(i));
        }
        ll.add("");
        Collections.shuffle(ll);
        for (int row = 0; row < level; row++)
            for (int col = 0; col < level; col++) {
                buttonsArray[row][col].setText(String.valueOf(ll.poll()));
            }
        startGame = Instant.now();
    }

    // ---------- Условие победы ---------- //
    public boolean victory() {
        int count = 1;
        for (int row = 0; row < level; row++) {
            for (int col = 0; col < level; col++) {
                String victoryText = String.valueOf(count);
                if (!buttonsArray[row][col].getText().equals(victoryText) && count < level * level)
                    return false;
                count++;
            }
        }
        return true;
    }

    private static String getWord(int buttonClick) {
        int remains = buttonClick % 100;

        if (remains > 9 && remains < 20) {
            return buttonClick + " ходов";
        } else {
            remains = remains % 10;
            if (remains == 1) {
                return buttonClick + " ход";
            } else if (remains > 1 && remains < 5) {
                return buttonClick + " хода";
            } else {
                return buttonClick + " ходов";
            }
        }
    }

    private void congratulations(String recordData, int recordClick) {
        String txt = getString(recordData, recordClick);

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

    private String getString(String record, int recordClick) {
        String recordText = "";

        if (!record.equals("0")) {
            if (buttonClick < recordClick) {
                recordText = "Вы установили рекорд!";
            } else recordText = "Рекорд " + record + " ходов!<br>";
        }

        return "<html><center><H2>Поздравляем!</H2><br>" +
               "Вы выиграли!<br>" +
               "Вы сделали: " + buttonClick + " ходов!<br>"
               + recordText + "<br>" +
               "Хотите еще сыграть?</center></html>";
    }

    // ---------- Ход игры --------- //
    private boolean isEmptyButton(int row1, int col1) {
        if (row1 < 0 || row1 >= level)
            return false;
        if (col1 < 0 || col1 >= level)
            return false;
        return buttonsArray[row1][col1].getText().isEmpty();
    }

    private void setTextB(int row1, int col1) {
        buttonsArray[row1][col1].setText(pressButton.getText());
        pressButton.setText("");
        buttonClick++;
    }

    public void actionPerformed(ActionEvent e) {
        pressButton = (JButton) e.getSource();
        for (int row = 0; row < buttonsArray.length; row++)
            for (int col = 0; col < buttonsArray[row].length; col++) {
                if (pressButton == buttonsArray[row][col]) {
                    clickButton.setText("Вы сделали " + getWord(buttonClick) + "!");
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
                        endGame = Instant.now();
                        saveScore(buttonClick);
                    }
                    return;
                }
            }
    }

    private void saveScore(int buttonClick) {
        ScoreDto scoreDto = scoreService.getScoreByLevel((long) level);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss:ms");
        Duration timeElapsed = Duration.between(startGame, endGame);
        LocalDateTime dateTime = LocalDateTime.now();
        NewScoreDto dto = NewScoreDto.builder()
                .level((long) level)
                .score((long) buttonClick)
                .gameTime(LocalTime.ofSecondOfDay(timeElapsed.getSeconds()).format(formatter))
                .created(dateTime)
                .build();
        scoreService.addScore(dto);

        congratulations(scoreDto.getScore().toString(), buttonClick);

    }
}