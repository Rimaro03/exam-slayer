package org.project.core.rendering;

import lombok.Getter;
import org.project.utils.Vec2;

import java.awt.image.BufferedImage;

@Getter
public class RenderableImage extends Renderable {
    private final BufferedImage image;

    public RenderableImage(BufferedImage image, Vec2 position, int priority) {
        super(position, priority);
        this.image = image;
    }
}
