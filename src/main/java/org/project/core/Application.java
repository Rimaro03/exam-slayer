package org.project.core;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.awt.*;


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
        window = new Window(1000, 600);
        setup();
    }

    private void runInternal() {
        // GAME INITIALIZATION HERE!
        Game game = Game.loadNewGame();

        addWindowListener(game);
        game.start();

        while (true) {
            long startTime = System.currentTimeMillis();

            game.update();
            window.update();

            while(System.currentTimeMillis() - startTime < Time.TIME_STEP_IN_MILLIS || !window.isFinishedPainting());
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
