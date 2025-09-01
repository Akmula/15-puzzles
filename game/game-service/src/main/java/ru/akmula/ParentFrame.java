package ru.akmula;

import ru.akmula.config.GameProperties;

import javax.swing.*;
import java.awt.*;

public class ParentFrame extends JFrame {

    public ParentFrame(GameProperties gameProperties) {
        new JFrame(gameProperties.getTitle());
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(gameProperties.getIcon()));
        this.setBounds(550, 150, 300, 450);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}