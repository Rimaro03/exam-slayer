package org.project.core;

import lombok.Getter;

public class Time implements GameStateListener {
    public static final int TIME_STEP_IN_MILLIS = 20;
    private static final float TIME_STEP_IN_SECONDS = TIME_STEP_IN_MILLIS / 1000.0f;
    private static final long START_TIME = System.currentTimeMillis();

    private boolean paused = false;

    public float unscaledDeltaTime() {
        return TIME_STEP_IN_SECONDS;
    }
    public float deltaTime() {
        return paused ? 0 : TIME_STEP_IN_SECONDS;
    }
    public double seconds() {
        return (System.currentTimeMillis() - START_TIME) / 1000.0f;
    }

    @Override
    public void onGamePaused() {
        paused = true;
    }

    @Override
    public void onGameResumed() {
        paused = false;
    }
}
