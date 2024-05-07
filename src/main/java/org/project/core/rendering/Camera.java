package org.project.core.rendering;

public class Camera {
    public static final Camera main = new Camera(64);

    /** Determine how many units are seen by the camera on the vertical view. */
    public final float verticalAmplitude;
    public Camera(float height) {
        this.verticalAmplitude = height;
    }
}
