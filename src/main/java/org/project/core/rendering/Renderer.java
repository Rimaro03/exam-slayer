package org.project.core.rendering;

import org.project.core.Application;
import org.project.utils.Vec2;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.util.PriorityQueue;

public class Renderer implements WindowStateListener {
    private static final int VERTICAL_RESOLUTION = 256;
    private static final int PIXEL_PER_UNIT_SPACE = 16;

    private static Renderer instance;

    private BufferedImage buffer;
    private BufferedImage uiBuffer;
    private final PriorityQueue<Renderable> renderQueue;

    private Renderer() {
        renderQueue = new PriorityQueue<>(new Renderable.Comparator());
        uiBuffer = new BufferedImage(
                Application.getWindow().getWidth(),
                Application.getWindow().getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
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

    public static void addCircleToRenderQueue(Vec2 position, float radius, Color color, int priority) { getInstance().renderQueue.add(new RenderableCircle(radius, color, position, priority)); }
    public static void addRectToRenderQueue(Vec2 position, Vec2 size, Color color, int priority) { getInstance().renderQueue.add(new RenderableRectangle(size, color, position, priority)); }
    public static void addImageToRenderQueue(BufferedImage sprite, Vec2 position, int priority) { getInstance().renderQueue.add(new RenderableImage(sprite, position, priority)); }
    public static void drawText(int x, int y, String text, Color color, int priority) { getInstance().drawText(text, x, y, color); }
    /** Applies the buffer to the screen. */
    public static void present(Graphics g) { getInstance().presentInternal(g); }



    /* -------------- INTERNAL METHODS ----------------- */

    private void clearInternal(Color color){
        Graphics g = buffer.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
    }
    private void drawImage(BufferedImage sprite, Vec2 position){
        Graphics g = buffer.getGraphics();

        g.drawImage(
                sprite,
                worldToScreenX(position.getX()) - sprite.getWidth(null) / 2,
                worldToScreenY(position.getY()) - sprite.getHeight(null) / 2,
                null
        );
    }
    private void drawRect(Vec2 position, Vec2 scale, Color color) {
        Graphics g = buffer.getGraphics();
        g.setColor(color);
        g.drawRect(
                worldToScreenX(position.getX() - scale.getX() / 2),
                worldToScreenY(position.getY() + scale.getY() / 2),
                worldToScreenWidth(scale.getX()),
                worldToScreenHeight(scale.getY())
        );
    }
    private void drawCircle(Vec2 position, float radius, Color color) {
        Graphics g = buffer.getGraphics();
        g.setColor(color);
        g.drawOval(
                worldToScreenX(position.getX() - radius),
                worldToScreenY(position.getY() + radius),
                worldToScreenWidth(radius * 2),
                worldToScreenHeight(radius * 2)
        );
    }

    private void drawText(String text, int x, int y, Color color) {
        Graphics g = uiBuffer.getGraphics();
        g.setColor(color);
        g.drawString(text, x, y);
    }

    private void presentInternal(Graphics g){
        // Draw all renderables
        while(!renderQueue.isEmpty()){
            Renderable renderable = renderQueue.poll();
            if(renderable instanceof RenderableImage){
                RenderableImage renderableImage = (RenderableImage) renderable;
                drawImage(renderableImage.getImage(), renderable.getPosition());
            } else if(renderable instanceof RenderableRectangle){
                RenderableRectangle renderableRectangle = (RenderableRectangle) renderable;
                drawRect(renderableRectangle.getPosition(), renderableRectangle.getSize(), renderableRectangle.getColor());
            } else if(renderable instanceof RenderableCircle){
                RenderableCircle renderableCircle = (RenderableCircle) renderable;
                drawCircle(renderableCircle.getPosition(), renderableCircle.getRadius(), renderableCircle.getColor());
            } else {
                throw new RuntimeException("Unknown renderable type");
            }
        }

        // Render buffer
        g.drawImage(buffer,
                0,
                0,
                Application.getWindow().getWidth(),
                Application.getWindow().getHeight(),
                null
        );
        g.drawImage(uiBuffer, 0, 0, null);
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
        uiBuffer = new BufferedImage(
                e.getWindow().getWidth(),
                e.getWindow().getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
    }
}
