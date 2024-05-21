package org.project.core.rendering;

import lombok.Getter;
import org.project.utils.Vec2;

import java.awt.*;

@Getter
public abstract class Renderable {
    protected final Vec2 position;
    private final int priority;

    public Renderable(Vec2 position, int priority) {
        this.position = position;
        this.priority = priority;
    }

    public static class Comparator implements java.util.Comparator<Renderable> {
        @Override
        public int compare(Renderable o1, Renderable o2) {
            return o1.getPriority() - o2.getPriority();
        }
    }

    public abstract void draw(Graphics g);
}
