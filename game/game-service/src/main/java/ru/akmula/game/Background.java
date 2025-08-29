package ru.akmula.game;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

@Slf4j
public record Background(String bgImages) {

    public ImagePanel getImagePanel() {

        ImagePanel bgImage = new ImagePanel();

        try {
            bgImage.setImage(ImageIO.read(new File(bgImages)));
        } catch (
                IOException e) {
            log.error(e.getMessage());
        }
        return bgImage;
    }
}