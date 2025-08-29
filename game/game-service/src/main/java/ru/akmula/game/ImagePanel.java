package ru.akmula.game;

import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Setter
public class ImagePanel extends JPanel {

    private Image image;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
    }
}