package ru.akmula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.akmula.game.Menu;

import java.awt.*;

@SpringBootApplication
public class GameApp {
    public static void main(String[] args) {

        System.setProperty("java.awt.headless", "false");
        ApplicationContext context = SpringApplication.run(GameApp.class, args);
        EventQueue.invokeLater(() -> {
            Menu window = context.getBean(Menu.class);
        //   mainFrame.init();
            window.init();
            //window.setVisible(true);
        });
    }
}