package org.project.core.rendering;

import lombok.Getter;
import org.project.utils.Vec2;

import java.awt.*;

@Getter
public class RenderableText extends Renderable {
    private final String text;
    private final int size;
    private final Color color;

    public RenderableText(String text, Vec2 position, int size, Color color, int priority) {
        super(position, priority);
        this.text = text;
        this.size = size;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        Font currentFont = g.getFont();
        if (currentFont.getSize() != size) {
            g.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), size));
        }
        g.drawString(text, Renderer.worldToScreenX(position.getX()), Renderer.worldToScreenY(position.getY()));
    }
}
