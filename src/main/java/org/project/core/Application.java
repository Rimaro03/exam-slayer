package org.project.core;

import lombok.Getter;
import org.project.core.rendering.Renderer;

import javax.swing.*;
import java.awt.*;

/**
 * This abstract class permits to test various application type during development.
 */
public class Application extends JFrame {
    @Getter
    private static Application instance;
    private Window window;

    private Application() { }
    public static Window getWindow(){ return getInstance().window; }
    public static void init()
    {
        instance = new Application();
        instance.initInternal();
    }
    public static void run() { getInstance().runInternal(); }

    private void initInternal(){
        window = new Window(800, 600);

        add(window);
        pack();
        setTitle("Exam Slayer");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowStartX = (screenSize.width - window.getWidth()) / 2;
        int windowStartY = (screenSize.height - window.getHeight()) / 2;
        setLocation(windowStartX, windowStartY);
        setVisible(true);

    }
    private void runInternal(){
        while(true){
            long startTime = System.currentTimeMillis();

//            Renderer.fillRect(new Rectangle(20, 20, 50, 50), Color.BLUE);
            Schene.getInstance().update();
            window.update();

            long delta = System.currentTimeMillis() - startTime;
            try{
                long sleepTime = 20 - delta;
                if(sleepTime > 0) { Thread.sleep(sleepTime); }
            }
            catch (Exception ignore) { }
        }
    }
}
