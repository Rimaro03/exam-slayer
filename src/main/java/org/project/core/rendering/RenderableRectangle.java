package org.project.core.rendering;

import lombok.Getter;
import org.project.utils.Vec2;

import java.awt.*;

@Getter
public class RenderableRectangle extends Renderable {
    private final Vec2 size;
    private final Color color;
    private final boolean filled;

    public RenderableRectangle(Vec2 size, Color color, Vec2 position, int priority, boolean filled) {
        super(position, priority);
        this.size = size;
        this.color = color;
        this.filled = filled;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);

        if (filled) {
            g.fillRect(
                    Renderer.worldToScreenX(position.getX() - size.getX() / 2),
                    Renderer.worldToScreenY(position.getY() + size.getY() / 2),
                    Renderer.worldToScreenSize(size.getX()),
                    Renderer.worldToScreenSize(size.getY())
            );
        }
        else {
            g.drawRect(
                    Renderer.worldToScreenX(position.getX() - size.getX() / 2),
                    Renderer.worldToScreenY(position.getY() + size.getY() / 2),
                    Renderer.worldToScreenSize(size.getX()),
                    Renderer.worldToScreenSize(size.getY())
            );
        }
    }
}
