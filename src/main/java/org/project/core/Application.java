package org.project.core;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.awt.*;
import java.io.File;


@Log4j2
public class Application extends JFrame {
    @Getter
    private static Application instance;
    private Window window;

    private Application() {
    }

    /* ---------------- SINGLETON METHODS ----------------- */

    public static Window getWindow() {
        return getInstance().window;
    }

    public static void init() {
        instance = new Application();
        instance.initInternal();
    }

    public static void run() {
        getInstance().runInternal();
    }



    /* --------------- INTERNAL METHODS ------------- */

    private void initInternal() {
        window = new Window(800, 600);
        setup();
    }

    private void runInternal() {
        // GAME INITIALIZATION HERE!
        String path = "saved/saved_game.txt";
        boolean shouldDelete = JOptionPane.showConfirmDialog(
                null,
                "Do you want to delete the saved data?",
                "Delete Data",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION;
        Game game = Game.loadNewGame(path);
        if (shouldDelete) {
            File file = new File(path);
            if (file.exists() && !file.delete()) {
                log.warn("Failed to delete the file: {}", path);
            }
            Game.getSavingIO().getBucketManager().deleteFile(path);

            game = Game.loadNewGame(path);
        }


        addWindowListener(game);
        game.start();

        while (true) {
            long startTime = System.currentTimeMillis();

            game.update();
            window.update();

            long delta = System.currentTimeMillis() - startTime;
            try {
                long sleepTime = Time.TIME_STEP_IN_MILLIS - delta;
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            } catch (Exception ignore) {
            }
        }
    }

    private void setup() {
        add(window);
        pack();
        setTitle("Exam Slayer");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(windowCenteredX(), windowCenteredY());
        setVisible(true);
    }


    /* ----------------- HELPER FUNCTIONS ---------------*/
    private int windowCenteredX() {
        return (Toolkit.getDefaultToolkit().getScreenSize().width - window.getWidth()) / 2;
    }

    private int windowCenteredY() {
        return (Toolkit.getDefaultToolkit().getScreenSize().height - window.getWidth()) / 2;
    }
}
