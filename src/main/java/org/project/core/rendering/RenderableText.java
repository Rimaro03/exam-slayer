package org.project.core.rendering;

import lombok.Getter;

import java.awt.*;

@Getter
public class RenderableText {
    private final String text;
    private final int x;
    private final int y;
    private final Color color;

    public RenderableText(String text, int x, int y, Color color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
