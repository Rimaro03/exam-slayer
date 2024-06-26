package org.project.core;


/**
 * A listener that listens to the state of the game. whether it is paused or resumed.
 */
public interface GameStateListener {
    void onGamePaused();
    void onGameResumed();
}
