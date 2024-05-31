package org.project.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Input {
    /**
     * Constant for the left mouse button
     */
    public static final int MOUSE_BUTTON_LEFT = 0;
    /**
     * Constant for the middle mouse button
     */
    public static final int MOUSE_BUTTON_MIDDLE = 1;
    /**
     * Constant for the right mouse button
     */
    public static final int MOUSE_BUTTON_RIGHT = 2;
    /**
     * Constant for the ENTER virtual key_.
     */
    public static final int KEY_ENTER = '\n';
    /**
     * Constant for the BACK_SPACE virtual key_.
     */
    public static final int KEY_BACK_SPACE = '\b';
    /**
     * Constant for the TAB virtual key_.
     */
    public static final int KEY_TAB = '\t';
    /**
     * Constant for the CANCEL virtual key_.
     */
    public static final int KEY_CANCEL = 0x03;
    /**
     * Constant for the CLEAR virtual key_.
     */
    public static final int KEY_CLEAR = 0x0C;
    /**
     * Constant for the SHIFT virtual key_.
     */
    public static final int KEY_SHIFT = 0x10;


    /* -------------- INPUT KEY AND MOUSE BUTTONS CONSTANTS ---------------*/
    /**
     * Constant for the CONTROL virtual key_.
     */
    public static final int KEY_CONTROL = 0x11;
    /**
     * Constant for the ALT virtual key_.
     */
    public static final int KEY_ALT = 0x12;
    /**
     * Constant for the PAUSE virtual key_.
     */
    public static final int KEY_PAUSE = 0x13;
    /**
     * Constant for the CAPS_LOCK virtual key_.
     */
    public static final int KEY_CAPS_LOCK = 0x14;
    /**
     * Constant for the ESCAPE virtual key_.
     */
    public static final int KEY_ESCAPE = 0x1B;
    /**
     * Constant for the SPACE virtual key_.
     */
    public static final int KEY_SPACE = 0x20;
    /**
     * Constant for the PAGE_UP virtual key_.
     */
    public static final int KEY_PAGE_UP = 0x21;
    /**
     * Constant for the PAGE_DOWN virtual key_.
     */
    public static final int KEY_PAGE_DOWN = 0x22;
    /**
     * Constant for the END virtual key_.
     */
    public static final int KEY_END = 0x23;
    /**
     * Constant for the HOME virtual key_.
     */
    public static final int KEY_HOME = 0x24;
    /**
     * Constant for the non-numpad <b>left</b> arrow key_.
     *
     * @see #KEY_KP_LEFT
     */
    public static final int KEY_LEFT = 0x25;
    /**
     * Constant for the non-numpad <b>up</b> arrow key_.
     *
     * @see #KEY_KP_UP
     */
    public static final int KEY_UP = 0x26;
    /**
     * Constant for the non-numpad <b>right</b> arrow key_.
     *
     * @see #KEY_KP_RIGHT
     */
    public static final int KEY_RIGHT = 0x27;
    /**
     * Constant for the non-numpad <b>down</b> arrow key_.
     *
     * @see #KEY_KP_DOWN
     */
    public static final int KEY_DOWN = 0x28;
    /**
     * Constant for the comma key_, ","
     */
    public static final int KEY_COMMA = 0x2C;
    /**
     * Constant for the minus key_, "-"
     *
     * @since 1.2
     */
    public static final int KEY_MINUS = 0x2D;
    /**
     * Constant for the period key_, "."
     */
    public static final int KEY_PERIOD = 0x2E;
    /**
     * Constant for the forward slash key_, "/"
     */
    public static final int KEY_SLASH = 0x2F;
    /**
     * Constant for the "0" key_.
     */
    public static final int KEY_0 = 0x30;
    /**
     * Constant for the "1" key_.
     */
    public static final int KEY_1 = 0x31;
    /**
     * Constant for the "2" key_.
     */
    public static final int KEY_2 = 0x32;
    /**
     * Constant for the "3" key_.
     */
    public static final int KEY_3 = 0x33;
    /**
     * Constant for the "4" key_.
     */
    public static final int KEY_4 = 0x34;
    /**
     * Constant for the "5" key_.
     */
    public static final int KEY_5 = 0x35;
    /**
     * Constant for the "6" key_.
     */
    public static final int KEY_6 = 0x36;
    /**
     * Constant for the "7" key_.
     */
    public static final int KEY_7 = 0x37;
    /**
     * Constant for the "8" key_.
     */
    public static final int KEY_8 = 0x38;

    /** KEY_0 thru KEY_9 are the same as ASCII '0' thru '9' (0x30 - 0x39) */
    /**
     * Constant for the "9" key_.
     */
    public static final int KEY_9 = 0x39;
    /**
     * Constant for the semicolon key_, ";"
     */
    public static final int KEY_SEMICOLON = 0x3B;
    /**
     * Constant for the equals key_, "="
     */
    public static final int KEY_EQUALS = 0x3D;
    /**
     * Constant for the "A" key_.
     */
    public static final int KEY_A = 0x41;
    /**
     * Constant for the "B" key_.
     */
    public static final int KEY_B = 0x42;
    /**
     * Constant for the "C" key_.
     */
    public static final int KEY_C = 0x43;
    /**
     * Constant for the "D" key_.
     */
    public static final int KEY_D = 0x44;
    /**
     * Constant for the "E" key_.
     */
    public static final int KEY_E = 0x45;
    /**
     * Constant for the "F" key_.
     */
    public static final int KEY_F = 0x46;
    /**
     * Constant for the "G" key_.
     */
    public static final int KEY_G = 0x47;
    /**
     * Constant for the "H" key_.
     */
    public static final int KEY_H = 0x48;
    /**
     * Constant for the "I" key_.
     */
    public static final int KEY_I = 0x49;

    /** KEY_A thru KEY_Z are the same as ASCII 'A' thru 'Z' (0x41 - 0x5A) */
    /**
     * Constant for the "J" key_.
     */
    public static final int KEY_J = 0x4A;
    /**
     * Constant for the "K" key_.
     */
    public static final int KEY_K = 0x4B;
    /**
     * Constant for the "L" key_.
     */
    public static final int KEY_L = 0x4C;
    /**
     * Constant for the "M" key_.
     */
    public static final int KEY_M = 0x4D;
    /**
     * Constant for the "N" key_.
     */
    public static final int KEY_N = 0x4E;
    /**
     * Constant for the "O" key_.
     */
    public static final int KEY_O = 0x4F;
    /**
     * Constant for the "P" key_.
     */
    public static final int KEY_P = 0x50;
    /**
     * Constant for the "Q" key_.
     */
    public static final int KEY_Q = 0x51;
    /**
     * Constant for the "R" key_.
     */
    public static final int KEY_R = 0x52;
    /**
     * Constant for the "S" key_.
     */
    public static final int KEY_S = 0x53;
    /**
     * Constant for the "T" key_.
     */
    public static final int KEY_T = 0x54;
    /**
     * Constant for the "U" key_.
     */
    public static final int KEY_U = 0x55;
    /**
     * Constant for the "V" key_.
     */
    public static final int KEY_V = 0x56;
    /**
     * Constant for the "W" key_.
     */
    public static final int KEY_W = 0x57;
    /**
     * Constant for the "X" key_.
     */
    public static final int KEY_X = 0x58;
    /**
     * Constant for the "Y" key_.
     */
    public static final int KEY_Y = 0x59;
    /**
     * Constant for the "Z" key_.
     */
    public static final int KEY_Z = 0x5A;
    /**
     * Constant for the open bracket key_, "["
     */
    public static final int KEY_OPEN_BRACKET = 0x5B;
    /**
     * Constant for the back slash key_, "\"
     */
    public static final int KEY_BACK_SLASH = 0x5C;
    /**
     * Constant for the close bracket key_, "]"
     */
    public static final int KEY_CLOSE_BRACKET = 0x5D;
    /**
     * Constant for the number pad "0" key_.
     */
    public static final int KEY_NUMPAD0 = 0x60;
    /**
     * Constant for the number pad "1" key_.
     */
    public static final int KEY_NUMPAD1 = 0x61;
    /**
     * Constant for the number pad "2" key_.
     */
    public static final int KEY_NUMPAD2 = 0x62;
    /**
     * Constant for the number pad "3" key_.
     */
    public static final int KEY_NUMPAD3 = 0x63;
    /**
     * Constant for the number pad "4" key_.
     */
    public static final int KEY_NUMPAD4 = 0x64;
    /**
     * Constant for the number pad "5" key_.
     */
    public static final int KEY_NUMPAD5 = 0x65;
    /**
     * Constant for the number pad "6" key_.
     */
    public static final int KEY_NUMPAD6 = 0x66;
    /**
     * Constant for the number pad "7" key_.
     */
    public static final int KEY_NUMPAD7 = 0x67;
    /**
     * Constant for the number pad "8" key_.
     */
    public static final int KEY_NUMPAD8 = 0x68;
    /**
     * Constant for the number pad "9" key_.
     */
    public static final int KEY_NUMPAD9 = 0x69;
    /**
     * Constant for the number pad multiply key_.
     */
    public static final int KEY_MULTIPLY = 0x6A;
    /**
     * Constant for the number pad add key_.
     */
    public static final int KEY_ADD = 0x6B;
    /**
     * This constant is obsolete, and is included only for backwards
     * compatibility.
     *
     * @see #KEY_SEPARATOR
     */
    public static final int KEY_SEPARATER = 0x6C;
    /**
     * Constant for the Numpad Separator key_.
     *
     * @since 1.4
     */
    public static final int KEY_SEPARATOR = KEY_SEPARATER;
    /**
     * Constant for the number pad subtract key_.
     */
    public static final int KEY_SUBTRACT = 0x6D;
    /**
     * Constant for the number pad decimal point key_.
     */
    public static final int KEY_DECIMAL = 0x6E;
    /**
     * Constant for the number pad divide key_.
     */
    public static final int KEY_DIVIDE = 0x6F;
    /**
     * Constant for the delete key_.
     */
    public static final int KEY_DELETE = 0x7F; /* ASCII DEL */
    /**
     * Constant for the NUM_LOCK key_.
     */
    public static final int KEY_NUM_LOCK = 0x90;
    /**
     * Constant for the SCROLL_LOCK key_.
     */
    public static final int KEY_SCROLL_LOCK = 0x91;
    /**
     * Constant for the F1 function key_.
     */
    public static final int KEY_F1 = 0x70;
    /**
     * Constant for the F2 function key_.
     */
    public static final int KEY_F2 = 0x71;
    /**
     * Constant for the F3 function key_.
     */
    public static final int KEY_F3 = 0x72;
    /**
     * Constant for the F4 function key_.
     */
    public static final int KEY_F4 = 0x73;
    /**
     * Constant for the F5 function key_.
     */
    public static final int KEY_F5 = 0x74;
    /**
     * Constant for the F6 function key_.
     */
    public static final int KEY_F6 = 0x75;
    /**
     * Constant for the F7 function key_.
     */
    public static final int KEY_F7 = 0x76;
    /**
     * Constant for the F8 function key_.
     */
    public static final int KEY_F8 = 0x77;
    /**
     * Constant for the F9 function key_.
     */
    public static final int KEY_F9 = 0x78;
    /**
     * Constant for the F10 function key_.
     */
    public static final int KEY_F10 = 0x79;
    /**
     * Constant for the F11 function key_.
     */
    public static final int KEY_F11 = 0x7A;
    /**
     * Constant for the F12 function key_.
     */
    public static final int KEY_F12 = 0x7B;
    /**
     * Constant for the F13 function key_.
     *
     * @since 1.2
     */
    /* F13 - F24 are used on IBM 3270 key_board; use random range for constants. */
    public static final int KEY_F13 = 0xF000;
    /**
     * Constant for the F14 function key_.
     *
     * @since 1.2
     */
    public static final int KEY_F14 = 0xF001;
    /**
     * Constant for the F15 function key_.
     *
     * @since 1.2
     */
    public static final int KEY_F15 = 0xF002;
    /**
     * Constant for the F16 function key_.
     *
     * @since 1.2
     */
    public static final int KEY_F16 = 0xF003;
    /**
     * Constant for the F17 function key_.
     *
     * @since 1.2
     */
    public static final int KEY_F17 = 0xF004;
    /**
     * Constant for the F18 function key_.
     *
     * @since 1.2
     */
    public static final int KEY_F18 = 0xF005;
    /**
     * Constant for the F19 function key_.
     *
     * @since 1.2
     */
    public static final int KEY_F19 = 0xF006;
    /**
     * Constant for the F20 function key_.
     *
     * @since 1.2
     */
    public static final int KEY_F20 = 0xF007;
    /**
     * Constant for the F21 function key_.
     *
     * @since 1.2
     */
    public static final int KEY_F21 = 0xF008;
    /**
     * Constant for the F22 function key_.
     *
     * @since 1.2
     */
    public static final int KEY_F22 = 0xF009;
    /**
     * Constant for the F23 function key_.
     *
     * @since 1.2
     */
    public static final int KEY_F23 = 0xF00A;
    /**
     * Constant for the F24 function key_.
     *
     * @since 1.2
     */
    public static final int KEY_F24 = 0xF00B;
    /**
     * Constant for the PRINTSCREEN key_.
     */
    public static final int KEY_PRINTSCREEN = 0x9A;
    /**
     * Constant for the INSERT key_.
     */
    public static final int KEY_INSERT = 0x9B;
    /**
     * Constant for the HELP key_.
     */
    public static final int KEY_HELP = 0x9C;
    /**
     * Constant for the META key_.
     */
    public static final int KEY_META = 0x9D;
    /**
     * Constant for the BACK_QUOTE  key_.
     */
    public static final int KEY_BACK_QUOTE = 0xC0;
    /**
     * Constant for the QUOTE key_.
     */
    public static final int KEY_QUOTE = 0xDE;
    /**
     * Constant for the numeric key_pad <b>up</b> arrow key_.
     *
     * @see #KEY_UP
     * @since 1.2
     */
    public static final int KEY_KP_UP = 0xE0;
    /**
     * Constant for the numeric key_pad <b>down</b> arrow key_.
     *
     * @see #KEY_DOWN
     * @since 1.2
     */
    public static final int KEY_KP_DOWN = 0xE1;
    /**
     * Constant for the numeric key_pad <b>left</b> arrow key_.
     *
     * @see #KEY_LEFT
     * @since 1.2
     */
    public static final int KEY_KP_LEFT = 0xE2;
    /**
     * Constant for the numeric key_pad <b>right</b> arrow key_.
     *
     * @see #KEY_RIGHT
     * @since 1.2
     */
    public static final int KEY_KP_RIGHT = 0xE3;
    private static Input instance;
    private final KeyInput keyInput;
    private final MouseInput mouseInput;

    private Input() {
        keyInput = new KeyInput();
        mouseInput = new MouseInput();

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

    /* ---------------- INTERNAL LISTENER CLASSES ------------------*/
    private static class KeyInput implements KeyListener {
        private final ArrayList<Integer> keysPressed;

        private KeyInput() {
            keysPressed = new ArrayList<>();
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (!keysPressed.contains(e.getKeyCode())) {
                keysPressed.add(e.getKeyCode());
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            keysPressed.remove((Integer) e.getKeyCode());
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
}
