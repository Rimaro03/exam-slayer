package org.project.core.rendering;

import lombok.Getter;
import org.project.utils.Vec2;

import java.awt.*;
import java.awt.image.BufferedImage;

@Getter
public class RenderableImage extends Renderable {
    private final BufferedImage image;

    public RenderableImage(BufferedImage image, Vec2 position, int priority) {
        super(position, priority);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(
                image,
                Renderer.worldToScreenX(position.getX()) - image.getWidth(null) / 2,
                Renderer.worldToScreenY(position.getY()) - image.getHeight(null) / 2,
                null
        );
    }
}
