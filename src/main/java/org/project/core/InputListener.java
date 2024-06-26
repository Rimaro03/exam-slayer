package org.project.core;

/**
 * A listener that listens to the input events of the game like key presses, typed and releases.
 */
public interface InputListener {
    void onKeyPressed(int keyCode);
    void onKeyReleased(int keyCode);
    void onKeyTyped(char keyChar);
}
