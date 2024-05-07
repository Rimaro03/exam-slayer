package org.project.core.rendering;

public class DrawCall {
    public final int priority;
    //public final SpriteData spriteData;
    public DrawCall(int priority/*, SpriteData spriteData*/){
        this.priority = priority;
        //this.spriteData = spriteData;
    }

    public static class Comparator implements java.util.Comparator<DrawCall> {
        @Override
        public int compare(DrawCall o1, DrawCall o2) { return o1.priority - o2.priority; }
    }
}
