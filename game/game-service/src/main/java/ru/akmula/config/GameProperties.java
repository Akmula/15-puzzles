package ru.akmula.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties("game-service")
public class GameProperties {

    String title;
    String version;
    String icon;
    String logo;

    Images images;

    @Data
    public static class Images {

      //  private Map<String, String> images;
        String bgAbout;
        String bgGameField;
        String bgMenu;

        String buttonAbout;
        String buttonExit;
        String buttonExitLevel;
        String buttonHelp;
        String buttonNewGame;
        String buttonNewGameExit;
        String buttonStart;
        String buttonStartLevel;

    }


}