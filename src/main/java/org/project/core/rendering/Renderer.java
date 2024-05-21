package org.project.core.rendering;

import org.project.core.Application;
import org.project.utils.Vec2;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.PriorityQueue;

public class Renderer implements ComponentListener {
    private static final int VERTICAL_RESOLUTION = 256;
    private static final int PIXEL_PER_UNIT_SPACE = 16;

    private static Renderer instance;

    private BufferedImage buffer;
    private final PriorityQueue<Renderable> renderQueue;


    private Renderer() {
        renderQueue = new PriorityQueue<>(new Renderable.Comparator());

        buffer = new BufferedImage(
                VERTICAL_RESOLUTION * Application.getWindow().getWidth() / Application.getWindow().getHeight(),
                VERTICAL_RESOLUTION,
                BufferedImage.TYPE_INT_ARGB
        );

        //Application.getInstance().addWindowStateListener(this);
        Application.getInstance().addComponentListener(this);
    }



    /* --------------- SINGLETON METHODS --------------- */

    public static Renderer getInstance() {
        if(instance == null){ instance = new Renderer(); }
        return instance;
    }

    /** Clears the buffer with the given color */
    public static void clear(Color color){ getInstance().clearInternal(color);}

    /** Adds a circle to the render queue
     * @param position The world position of the circle
     * @param radius The radius of the circle
     * @param color The color of the circle
     * @param priority The priority of the circle higher priority will be rendered last
     */
    public static void addCircleToRenderQueue(Vec2 position, float radius, Color color, int priority) { getInstance().renderQueue.add(new RenderableCircle(radius, color, position, priority)); }
    /** Adds a rectangle to the render queue
     * @param position The world position of the rectangle
     * @param size A vec2 representing the width and height of the rectangle
     * @param color The color of the circle
     * @param priority The priority of the circle higher priority will be rendered last
     */
    public static void addRectToRenderQueue(Vec2 position, Vec2 size, Color color, int priority) { getInstance().renderQueue.add(new RenderableRectangle(size, color, position, priority)); }
    /** Adds an image to the render queue
     * @param position The world position of the image
     * @param sprite The image buffer to render
     * @param priority The priority of the circle higher priority will be rendered last
     */
    public static void addImageToRenderQueue(Vec2 position, BufferedImage sprite, int priority) { getInstance().renderQueue.add(new RenderableImage(sprite, position, priority)); }
    /** Adds text to the render queue
     * @param position The world position of the text
     * @param text The text to render
     * @param color The color of the text
     * @param size The size of the text
     * @param priority The priority of the circle higher priority will be rendered last
     */
    public static void addTextToRenderQueue(Vec2 position, String text, Color color, int size, int priority) { getInstance().renderQueue.add(new RenderableText(text, position, size, color, priority)); }
    /** Applies the buffer to the screen. */
    public static void present(Graphics g) { getInstance().presentInternal(g); }

    /** Converts a world x position to a screen x position */
    public static int worldToScreenX(float x) { return getInstance().worldToScreenXInternal(x); }
    /** Converts a world y position to a screen y position */
    public static int worldToScreenY(float y) { return getInstance().worldToScreenYInternal(y); }
    /** Converts a world size to a screen size */
    public static int worldToScreenSize(float size) { return getInstance().worldToScreenSizeInternal(size); }

    /* -------------- INTERNAL METHODS ----------------- */

    private void clearInternal(Color color){
        Graphics g = buffer.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
    }

    private void presentInternal(Graphics g){
        // Draw all renderables
        Graphics bufferGraphics = buffer.getGraphics();
        while(!renderQueue.isEmpty()){
            renderQueue.poll().draw(bufferGraphics);
        }

        // Render buffer
        g.drawImage(buffer,
                0,
                0,
                Application.getWindow().getWidth(),
                Application.getWindow().getHeight(),
                null
        );
        clear(Color.gray);
    }

    /* -------------- HELPER METHODS ----------------- */
    public int worldToScreenXInternal(float x) {
        return (int)(x * PIXEL_PER_UNIT_SPACE + buffer.getWidth() * 0.5f);
    }
    public int worldToScreenYInternal(float y) {
        return (int)(buffer.getHeight() * 0.5f - y * PIXEL_PER_UNIT_SPACE);
    }
    public int worldToScreenSizeInternal(float size) {
        return (int)(size * PIXEL_PER_UNIT_SPACE);
    }

    /* -------------------WINDOW STATE LISTENER ----------------*/

    @Override
    public void componentResized(ComponentEvent e) {
        buffer = new BufferedImage(
                VERTICAL_RESOLUTION * e.getComponent().getWidth() / e.getComponent().getHeight(),
                VERTICAL_RESOLUTION,
                BufferedImage.TYPE_INT_ARGB
        );
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
