package org.project.core;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;


public class Application extends JFrame {
    @Getter
    private static Application instance;
    private Window window;

    private Application() { }



    /* ---------------- SINGLETON METHODS ----------------- */

    public static Window getWindow(){ return getInstance().window; }
    public static void init() {
        instance = new Application();
        instance.initInternal();
    }
    public static void run() { getInstance().runInternal(); }



    /* --------------- INTERNAL METHODS ------------- */

    private void initInternal(){
        window = new Window(800, 600);
        setup();
    }
    private void runInternal(){
        // GAME INITIALIZATION HERE!
        Scheme.getInstance().start();
        while(true){
            long startTime = System.currentTimeMillis();

            Scheme.getInstance().update();
            window.update();

            long delta = System.currentTimeMillis() - startTime;
            try{
                long sleepTime = 20 - delta;
                if(sleepTime > 0) { Thread.sleep(sleepTime); }
            }
            catch (Exception ignore) { }
        }
    }
    private void setup(){
        add(window);
        pack();
        setTitle("Exam Slayer");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(windowCenteredX(), windowCenteredY());
        setVisible(true);
    }


    /* ----------------- HELPER FUNCTIONS ---------------*/
    private int windowCenteredX() { return (Toolkit.getDefaultToolkit().getScreenSize().width - window.getWidth()) / 2; }
    private int windowCenteredY() { return (Toolkit.getDefaultToolkit().getScreenSize().height - window.getWidth()) / 2; }
}
