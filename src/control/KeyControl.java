package control;

import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class KeyControl implements GLFWKeyCallbackI {
    private static final int NUM_KEYS;
    public static final int // todo replace with enum
            KEY_W, KEY_A, KEY_S, KEY_D,
            KEY_Q, KEY_E,
            KEY_R, KEY_F, KEY_Z, KEY_X,
            KEY_SHIFT, KEY_SPACE,
            KEY_ENTER,
            KEY_C, KEY_V, KEY_I, KEY_B, KEY_M;

    static {
        int i = 0;
        KEY_W = i++;
        KEY_A = i++;
        KEY_S = i++;
        KEY_D = i++;
        KEY_Q = i++;
        KEY_E = i++;
        KEY_R = i++;
        KEY_F = i++;
        KEY_Z = i++;
        KEY_X = i++;
        KEY_SHIFT = i++;
        KEY_SPACE = i++;
        KEY_ENTER = i++;
        KEY_C = i++;
        KEY_V = i++;
        KEY_I = i++;
        KEY_B = i++;
        KEY_M = i++;
        NUM_KEYS = i;
    }

    private Key[] keys;

    public KeyControl(long window) {
        glfwSetKeyCallback(window, this);
        keys = new Key[NUM_KEYS];
        keys[KEY_W] = new Key(GLFW_KEY_W);
        keys[KEY_A] = new Key(GLFW_KEY_A);
        keys[KEY_S] = new Key(GLFW_KEY_S);
        keys[KEY_D] = new Key(GLFW_KEY_D);
        keys[KEY_Q] = new Key(GLFW_KEY_Q);
        keys[KEY_E] = new Key(GLFW_KEY_E);
        keys[KEY_R] = new Key(GLFW_KEY_R);
        keys[KEY_Z] = new Key(GLFW_KEY_Z);
        keys[KEY_X] = new Key(GLFW_KEY_X);
        keys[KEY_F] = new Key(GLFW_KEY_F);
        keys[KEY_SHIFT] = new Key(GLFW_KEY_LEFT_SHIFT);
        keys[KEY_SPACE] = new Key(GLFW_KEY_SPACE);
        keys[KEY_ENTER] = new Key(GLFW_KEY_ENTER);
        keys[KEY_C] = new Key(GLFW_KEY_C);
        keys[KEY_V] = new Key(GLFW_KEY_V);
        keys[KEY_I] = new Key(GLFW_KEY_I);
        keys[KEY_B] = new Key(GLFW_KEY_B);
        keys[KEY_M] = new Key(GLFW_KEY_M);
    }

    private void setKeyState(int keyCode, State state) {
        Key key = getKey(keyCode);
        if (key != null)
            key.state = state;
    }

    public boolean isKeyPressed(int keyIndex) {
        Key key = keys[keyIndex];
        if (key.state == State.PRESSED) {
            key.state = State.DOWN;
            return true;
        }
        return false;
    }

    public boolean isKeyDown(int keyIndex) {
        Key key = keys[keyIndex];
        if (key.state == State.PRESSED)
            key.state = State.DOWN;
        return key.state == State.DOWN;
    }

    private Key getKey(int keyCode) {
        for (Key key : keys)
            if (key.keyCode == keyCode)
                return key;
        return null;
    }

    @Override
    public void invoke(long window, int keyCode, int scancode, int action, int mods) {
        if (keyCode == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
            glfwSetWindowShouldClose(window, true);
        else if (action == GLFW_RELEASE)
            setKeyState(keyCode, State.RELEASED);
        else if (action == GLFW_PRESS)
            setKeyState(keyCode, State.PRESSED);
    }

    private class Key {
        private int keyCode;
        private State state;

        private Key(int keyCode) {
            this.keyCode = keyCode;
        }
    }
}