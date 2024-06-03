package org.project.core;

public class Time implements GameStateListener {
    public static final int TIME_STEP_IN_MILLIS = 20;
    private static final float TIME_STEP_IN_SECONDS = TIME_STEP_IN_MILLIS / 1000.0f;
    private static final long START_TIME = System.currentTimeMillis();

    private float timeScale = 1;

    public float unscaledDeltaTime() {
        return TIME_STEP_IN_SECONDS;
    }
    public float deltaTime() {
        return TIME_STEP_IN_SECONDS * timeScale;
    }
    public double seconds() {
        return (System.currentTimeMillis() - START_TIME) / 1000.0f;
    }

    @Override
    public void onGamePaused() {
        timeScale = 0;
    }

    @Override
    public void onGameResumed() {
        timeScale = 1;
    }
}
