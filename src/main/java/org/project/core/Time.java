package org.project.core;

public class Time {
    public static final int TIME_STEP_IN_MILLIS = 20;
    private static final float TIME_STEP_IN_SECONDS = TIME_STEP_IN_MILLIS / 1000.0f;
    private static final long START_TIME = System.currentTimeMillis();

    public float deltaTime() {
        return TIME_STEP_IN_SECONDS;
    }
    public double seconds() {
        return (System.currentTimeMillis() - START_TIME) / 1000.0f;
    }

}
