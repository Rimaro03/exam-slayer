package org.project.core.rendering;

import org.project.core.Application;

import java.awt.*;
import java.util.PriorityQueue;
import java.util.Random;

public class Renderer {
    private static Renderer instance;
    //private final PriorityQueue<DrawCall> renderQueue;
    private Renderer() {
        //renderQueue = new PriorityQueue<>(new DrawCall.Comparator());
    }

    public static void init(){ instance = new Renderer(); }
    //public static void addToRenderQueue(DrawCall call){ instance.renderQueue.add(call); }
    public static void present(Graphics g) {
        // Test

        Random rand = new Random();
        g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        int r = 50 + rand.nextInt(150);
        g.fillOval(
                rand.nextInt(Application.getWindow().getWidth() - r),
                rand.nextInt(Application.getWindow().getHeight() - r),
                r,
                r
        );
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        /*while(!instance.renderQueue.isEmpty()){
            DrawCall drawCall = instance.renderQueue.poll();
            // draw
        }*/
    }
}
