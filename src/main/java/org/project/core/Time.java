package org.project.core;

/**
 * A class that keeps track of the time since the game started and the time step of the game.
 * The time step of the game is fixed at 25 milliseconds.
 */
public class Time {
    public static final int TIME_STEP_IN_MILLIS = 25;
    private static final float TIME_STEP_IN_SECONDS = TIME_STEP_IN_MILLIS / 1000.0f;
    private static final long START_TIME = System.currentTimeMillis();

    public float deltaTime() {
        return TIME_STEP_IN_SECONDS;
    }
    public double seconds() {
        return (System.currentTimeMillis() - START_TIME) / 1000.0f;
    }

}
