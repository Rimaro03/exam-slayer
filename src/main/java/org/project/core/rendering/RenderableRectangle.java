package org.project.core.rendering;

import lombok.Getter;
import org.project.utils.Vec2;

import java.awt.*;

@Getter
public class RenderableRectangle extends Renderable {
    private final Vec2 size;
    private final Color color;

    public RenderableRectangle(Vec2 size, Color color, Vec2 position, int priority) {
        super(position, priority);
        this.size = size;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect(
                Renderer.worldToScreenX(position.getX() - size.getX() / 2),
                Renderer.worldToScreenY(position.getY() + size.getY() / 2),
                Renderer.worldToScreenSize(size.getX()),
                Renderer.worldToScreenSize(size.getY())
        );
    }
}
