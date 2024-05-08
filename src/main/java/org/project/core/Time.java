package org.project.core;

public class Time {
    private static final long startTime = System.currentTimeMillis();
    public static double seconds() {
        return (System.currentTimeMillis() - startTime) / 1000.0f;
    }
}
