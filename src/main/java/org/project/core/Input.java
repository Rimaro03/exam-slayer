package org.project.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * The Input class is a singleton class that represents the input system of the game.
 * It contains information about the keys and mouse buttons that are currently pressed.
 */
public class Input {
    private static Input instance;
    private final KeyInput keyInput;
    private final MouseInput mouseInput;
    private final ArrayList<InputListener> inputListeners;

    private Input() {
        keyInput = new KeyInput();
        mouseInput = new MouseInput();
        inputListeners = new ArrayList<>();

        Application.getWindow().addKeyListener(keyInput);
        Application.getWindow().addMouseListener(mouseInput);
    }

    /* ---------------- SINGLETON METHODS ------------------- */
    public static Input getInstance() {
        if (instance == null) {
            instance = new Input();
        }
        return instance;
    }

    public static boolean isKeyPressed(int keycode) {
        return getInstance().keyInput.keysPressed.contains(keycode);
    }

    public static boolean isMouseButtonPressed(int button) {
        return getInstance().mouseInput.buttons[button];
    }

    public static void addInputListener(InputListener listener) {
        getInstance().inputListeners.add(listener);
    }

    public static void removeInputListener(InputListener listener) {
        getInstance().inputListeners.remove(listener);
    }

    /* ---------------- INTERNAL LISTENER CLASSES ------------------*/
    private class KeyInput implements KeyListener {
        private final ArrayList<Integer> keysPressed;

        private KeyInput() {
            keysPressed = new ArrayList<>();
        }

        @Override
        public void keyTyped(KeyEvent e) {
            for (InputListener listener : inputListeners)
                listener.onKeyTyped(e.getKeyChar());
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (!keysPressed.contains(e.getKeyCode())) {
                keysPressed.add(e.getKeyCode());
            }

            for (InputListener listener : inputListeners)
                listener.onKeyPressed(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            keysPressed.remove((Integer) e.getKeyCode());

            for (InputListener listener : inputListeners)
                listener.onKeyReleased(e.getKeyCode());
        }
    }

    private static class MouseInput implements MouseListener {
        private final boolean[] buttons;

        private MouseInput() {
            buttons = new boolean[3];
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            buttons[e.getButton() - 1] = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            buttons[e.getButton() - 1] = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }


    /**
     * Constant for the ENTER virtual key_.
     */
    public static final int KEY_ENTER = '\n';
    /**
     * Constant for the BACK_SPACE virtual key_.
     */
    public static final int KEY_BACK_SPACE = '\b';


    /* -------------- INPUT KEY AND MOUSE BUTTONS CONSTANTS ---------------*/

    /**
     * Constant for the ESCAPE virtual key_.
     */
    public static final int KEY_ESCAPE = 0x1B;

    /**
     * Constant for the non-numpad <b>left</b> arrow key_.
     *
     */
    public static final int KEY_LEFT = 0x25;
    /**
     * Constant for the non-numpad <b>up</b> arrow key_.
     *
     */
    public static final int KEY_UP = 0x26;
    /**
     * Constant for the non-numpad <b>right</b> arrow key_.
     *
     */
    public static final int KEY_RIGHT = 0x27;
    /**
     * Constant for the non-numpad <b>down</b> arrow key_.
     *
     */
    public static final int KEY_DOWN = 0x28;
    /**
     * Constant for the "A" key_.
     */
    public static final int KEY_A = 0x41;

    /**
     * Constant for the "D" key_.
     */
    public static final int KEY_D = 0x44;
    /**
     * Constant for the "S" key_.
     */
    public static final int KEY_S = 0x53;
    /**
     * Constant for the "W" key_.
     */
    public static final int KEY_W = 0x57;


}
