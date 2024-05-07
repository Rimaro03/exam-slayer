package org.project.core.rendering;

import org.project.core.Application;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;

public class Renderer implements WindowStateListener {
    private static Renderer instance;
    private BufferedImage buffer;
    private Renderer() {
        buffer = new BufferedImage(Application.getWindow().getWidth(), Application.getWindow().getHeight(), BufferedImage.TYPE_INT_ARGB);

        Application.getInstance().addWindowStateListener(this);
    }
    public static Renderer getInstance() {
        if(instance == null){ instance = new Renderer(); }
        return instance;
    }
    public static void clear(Color color){ getInstance().clearInternal(color);}
    public static void fillRect(Rectangle rect, Color color){ getInstance().fillRectInternal(rect, color); }
    public static void present(Graphics g) { getInstance().presentInternal(g); }


    private void clearInternal(Color color){
        Graphics g = buffer.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, instance.buffer.getWidth(), instance.buffer.getHeight());
    }
    private void fillRectInternal(Rectangle rect, Color color) {
        Graphics g = buffer.getGraphics();
        g.setColor(color);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }
    private void presentInternal(Graphics g){
        g.drawImage(buffer,
                0,
                0,
                Application.getWindow().getWidth(),
                Application.getWindow().getHeight(),
                null
        );
        clear(Color.GRAY);
    }
    @Override
    public void windowStateChanged(WindowEvent e) {
        buffer = new BufferedImage(
                Application.getWindow().getWidth(),
                Application.getWindow().getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
    }
}
