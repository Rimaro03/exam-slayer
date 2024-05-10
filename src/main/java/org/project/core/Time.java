package org.project.core;

public class Time {
    private static final long START_TIME = System.currentTimeMillis();
    public static final int TIME_STEP_IN_MILLIS = 20;
    public static final float TIME_STEP_IN_SECONDS = TIME_STEP_IN_MILLIS / 1000.0f;
    public static double seconds() {
        return (System.currentTimeMillis() - START_TIME) / 1000.0f;
    }
}
