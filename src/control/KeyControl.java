package control;

import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class KeyControl implements GLFWKeyCallbackI {
    private Key[] keys;

    public KeyControl(long window) {
        glfwSetKeyCallback(window, this);
        keys = new Key[KeyButton.values().length];
        keys[KeyButton.KEY_W.value] = new Key(GLFW_KEY_W);
        keys[KeyButton.KEY_A.value] = new Key(GLFW_KEY_A);
        keys[KeyButton.KEY_S.value] = new Key(GLFW_KEY_S);
        keys[KeyButton.KEY_D.value] = new Key(GLFW_KEY_D);
        keys[KeyButton.KEY_Q.value] = new Key(GLFW_KEY_Q);
        keys[KeyButton.KEY_E.value] = new Key(GLFW_KEY_E);
        keys[KeyButton.KEY_R.value] = new Key(GLFW_KEY_R);
        keys[KeyButton.KEY_Z.value] = new Key(GLFW_KEY_Z);
        keys[KeyButton.KEY_X.value] = new Key(GLFW_KEY_X);
        keys[KeyButton.KEY_F.value] = new Key(GLFW_KEY_F);
        keys[KeyButton.KEY_SHIFT.value] = new Key(GLFW_KEY_LEFT_SHIFT);
        keys[KeyButton.KEY_SPACE.value] = new Key(GLFW_KEY_SPACE);
        keys[KeyButton.KEY_ENTER.value] = new Key(GLFW_KEY_ENTER);
        keys[KeyButton.KEY_C.value] = new Key(GLFW_KEY_C);
        keys[KeyButton.KEY_V.value] = new Key(GLFW_KEY_V);
        keys[KeyButton.KEY_I.value] = new Key(GLFW_KEY_I);
        keys[KeyButton.KEY_B.value] = new Key(GLFW_KEY_B);
        keys[KeyButton.KEY_N.value] = new Key(GLFW_KEY_N);
        keys[KeyButton.KEY_M.value] = new Key(GLFW_KEY_M);
    }

    private void setKeyState(int keyCode, State state) {
        Key key = getKey(keyCode);
        if (key != null)
            key.state = state;
    }

    public boolean isKeyPressed(KeyButton keyButton) {
        Key key = keys[keyButton.value];
        if (key.state == State.PRESSED) {
            key.state = State.DOWN;
            return true;
        }
        return false;
    }

    public boolean isKeyDown(KeyButton keyButton) {
        Key key = keys[keyButton.value];
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