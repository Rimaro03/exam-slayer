package org.project.core;

import javax.swing.JFrame;
import java.awt.*;

/**
 * This abstract class permits to test various application type during development.
 */
public abstract class Application extends JFrame {
    protected static Application instance;
    protected final Window window;
    protected Application(String name) {
        instance = this;
        window = new Window(800, 600);

        add(window);
        pack();
        setTitle(name);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowStartX = (screenSize.width - window.getWidth()) / 2;
        int windowStartY = (screenSize.height - window.getHeight()) / 2;
        setLocation(windowStartX, windowStartY);
        setVisible(true);

        addWindowStateListener(window);
    }
    public static Window getWindow(){ return instance.window; }
    public static Application getApplication() { return instance; }
    public void run() { }
}
