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
    /** Clears the buffer with the given color */
    public static void clear(Color color){ getInstance().clearInternal(color);}
    /** Draws an image */
    public static void draw(DrawCall drawCall){ getInstance().drawInternal(drawCall); }
    /*** This is a test function and will be removed. */
    public static void fillRect(Rectangle rect, Color color){ getInstance().fillRectInternal(rect, color); }
    /** Applies the buffer to the screen. */
    public static void present(Graphics g) { getInstance().presentInternal(g); }
    private static int worldToScreenX(float x){
        return (int) ((x * Application.getWindow().getWidth() / (Camera.main.verticalAmplitude * Camera.main.verticalAmplitude) + 1) * 0.5f * Application.getWindow().getWidth());
    }
    private static int worldToScreenY(float y){
        return (int) ((y / Camera.main.verticalAmplitude + 1) * 0.5f * Application.getWindow().getHeight());
    }

    private void clearInternal(Color color){
        Graphics g = buffer.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, instance.buffer.getWidth(), instance.buffer.getHeight());
    }
    private void drawInternal(DrawCall drawCall){
        Graphics g = buffer.getGraphics();

        g.drawImage(
                drawCall.image,
                worldToScreenX(drawCall.position.getX()) - drawCall.image.getWidth(null) / 2,
                worldToScreenY(drawCall.position.getY()) - drawCall.image.getHeight(null) / 2,
                null
        );
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
                e.getWindow().getWidth(),
                e.getWindow().getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
    }
}
