package ru.akmula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class GameApp {
    public static void main(String[] args) {

        System.setProperty("java.awt.headless", "false");
        ApplicationContext context = SpringApplication.run(GameApp.class, args);
        EventQueue.invokeLater(() -> {
            Menu mainFrame = context.getBean(Menu.class);
           mainFrame.init();
        });
    }
    }