package org.project.core;

public interface InputListener {
    void onKeyPressed(int keyCode);
    void onKeyReleased(int keyCode);
    void onKeyTyped(char keyChar);
}
