package org.project.generation.wavecollapse;

public class Direction {
    /** Returns the opposite direction. */
    public static int opposite(int direction){ return (direction + 2) % 4; }
    /** Returns x moved over direction of 1 unit. */
    public static int x(int x, int direction){
        return x + (2 - direction) % 2;
    }
    /** Returns y moved over direction of 1 unit. */
    public static int y(int y, int direction){
        return y + (1 - direction) % 2;
    }
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
}
