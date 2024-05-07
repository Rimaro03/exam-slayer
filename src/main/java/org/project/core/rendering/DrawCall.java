package org.project.core.rendering;

import org.project.utils.Vec2;
import java.awt.Image;

public class DrawCall {
    public final Image image;
    public final Vec2 position;
    public DrawCall(Image image, Vec2 position){
        this.image = image;
        this.position = position;
    }
}
