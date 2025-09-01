package ru.akmula.game;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
@Setter
public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel(String pathImage) {

        try {
            log.info("Загружаем фоновое изображение!");
            image = ImageIO.read(new File(pathImage));
        } catch (
                IOException e) {
            log.error("Ошибка при загрузке фонового изображения: {}", e.getMessage());
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}