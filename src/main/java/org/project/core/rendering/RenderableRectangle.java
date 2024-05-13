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
}
