package org.project.core.rendering;

import org.project.core.Application;
import org.project.utils.Vec2;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;

public class Renderer implements WindowStateListener {
    private static final int VERTICAL_RESOLUTION = 256;

    private static Renderer instance;

    private BufferedImage buffer;

    private Renderer() {
        buffer = new BufferedImage(
                VERTICAL_RESOLUTION * Application.getWindow().getWidth() / Application.getWindow().getHeight(),
                VERTICAL_RESOLUTION,
                BufferedImage.TYPE_INT_ARGB
        );

        Application.getInstance().addWindowStateListener(this);
    }



    /* --------------- SINGLETON METHODS --------------- */

    public static Renderer getInstance() {
        if(instance == null){ instance = new Renderer(); }
        return instance;
    }

    /** Clears the buffer with the given color */
    public static void clear(Color color){ getInstance().clearInternal(color);}
    /** Draws an image */
    public static void draw(BufferedImage sprite, Vec2 position){ getInstance().drawInternal(sprite, position); }
    /*** This is a test function and will be removed. */
    public static void fillRect(Rectangle rect, Color color){ getInstance().fillRectInternal(rect, color); }
    /** Applies the buffer to the screen. */
    public static void present(Graphics g) { getInstance().presentInternal(g); }



    /* -------------- INTERNAL METHODS ----------------- */

    private void clearInternal(Color color){
        Graphics g = buffer.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
    }
    private void drawInternal(BufferedImage sprite, Vec2 position){
        Graphics g = buffer.getGraphics();

        g.drawImage(
                sprite,
                worldToScreenX(position.getX()) - sprite.getWidth(null) / 2,
                worldToScreenY(position.getY()) - sprite.getHeight(null) / 2,
                null
        );
    }
    private void fillRectInternal(Rectangle rect, Color color) {
        Graphics g = buffer.getGraphics();
        g.setColor(color);
        g.fillRect(
                worldToScreenX(rect.x) - rect.width / 2,
                worldToScreenY(rect.y) - rect.height / 2,
                rect.width,
                rect.height
        );
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



    /* -------------- HELPER METHODS ----------------- */

    private int worldToScreenX(float x){
        return (int) ((x * buffer.getHeight() / buffer.getWidth() / Camera.main.verticalAmplitude + 1.f) * 0.5f * buffer.getWidth());
    }
    private int worldToScreenY(float y) {
        return (int) ((y / Camera.main.verticalAmplitude + 1.f) * 0.5f * buffer.getHeight());
    }



    /* -------------------WINDOW STATE LISTENER ----------------*/

    @Override
    public void windowStateChanged(WindowEvent e) {
        buffer = new BufferedImage(
                VERTICAL_RESOLUTION * e.getWindow().getWidth() / e.getWindow().getHeight(),
                VERTICAL_RESOLUTION,
                BufferedImage.TYPE_INT_ARGB
        );
    }
}
