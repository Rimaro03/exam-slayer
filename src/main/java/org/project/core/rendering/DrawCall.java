package org.project.core.rendering;

import org.project.utils.Vec2;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class DrawCall {
    public final BufferedImage image;
    public final Vec2 position;
    public DrawCall(BufferedImage image, Vec2 position){
        this.image = image;
        this.position = position;
    }
}
