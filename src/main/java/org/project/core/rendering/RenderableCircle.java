package org.project.core.rendering;

import lombok.Getter;
import org.project.utils.Vec2;

import java.awt.*;

@Getter
public class RenderableCircle extends Renderable {
    private final float radius;
    private final Color color;
    public RenderableCircle(float radius, Color color, Vec2 position, int priority) {
        super(position, priority);
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        int diameter = Renderer.worldToScreenSize(this.radius * 2);
        g.drawOval(Renderer.worldToScreenX(position.getX()), Renderer.worldToScreenY(position.getY()), diameter, diameter);
    }
}
