package org.project.generation.wavecollapse;

/**
 * This contains functionalities to work with directions coded as :
 * 0 = UP, 1 = RIGHT, 2 = DOWN, 3 = LEFT
 */
public class Direction {
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;


    private Direction() {
    }

    /**
     * Returns the opposite direction.
     */
    public static int opposite(int direction) {
        return (direction + 2) % 4;
    }

    /**
     * Returns x moved over direction of 1 unit.
     */
    public static int x(int x, int direction) {
        return x + (2 - direction) % 2;
    }

    /**
     * Returns y moved over direction of 1 unit.
     */
    public static int y(int y, int direction) {
        return y + (1 - direction) % 2;
    }
}
