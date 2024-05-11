package org.project.core.rendering;

import org.project.core.Application;
import org.project.utils.Vec2;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;

public class Renderer implements WindowStateListener {
    private static final int VERTICAL_RESOLUTION = 256;
    private static final int PIXEL_PER_UNIT_SPACE = 16;

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
    /*** Draw a raw rectangle */
    public static void drawRect(Vec2 position, Vec2 scale, Color color){ getInstance().drawRectInternal(position, scale, color); }
    /*** Draw a raw circle **/
    public static void drawCircle(Vec2 position, float radius, Color color) { getInstance().drawCircleInternal(position, radius, color);}
    /** Draws a pixel */
    public static void drawPixel(Vec2 position, Color color){ getInstance().drawPixelInternal(position, color); }
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
    private void drawRectInternal(Vec2 position, Vec2 scale, Color color) {
        Graphics g = buffer.getGraphics();
        g.setColor(color);
        g.drawRect(
                worldToScreenX(position.getX() - scale.getX() / 2),
                worldToScreenY(position.getY() + scale.getY() / 2),
                worldToScreenWidth(scale.getX()),
                worldToScreenHeight(scale.getY())
        );
    }
    private void drawCircleInternal(Vec2 position, float radius, Color color) {
        Graphics g = buffer.getGraphics();
        g.setColor(color);
        g.drawOval(
                worldToScreenX(position.getX() - radius),
                worldToScreenY(position.getY() + radius),
                worldToScreenWidth(radius * 2),
                worldToScreenHeight(radius * 2)
        );
    }

    private void drawPixelInternal(Vec2 position, Color color){
        Graphics g = buffer.getGraphics();
        g.setColor(color);
        g.drawOval(
                worldToScreenX(position.getX()),
                worldToScreenY(position.getY()),
                1,
                1
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
    private int worldToScreenX(float x) {
        return (int)(x * PIXEL_PER_UNIT_SPACE + buffer.getWidth() * 0.5f);
    }
    private int worldToScreenY(float y) {
        return (int)(buffer.getHeight() * 0.5f - y * PIXEL_PER_UNIT_SPACE);
    }
    private int worldToScreenWidth(float width) {
        return (int)(width * PIXEL_PER_UNIT_SPACE);
    }
    private int worldToScreenHeight(float height) {
        return (int)(height * PIXEL_PER_UNIT_SPACE);
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
