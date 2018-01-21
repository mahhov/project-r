package control;

import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class MouseButtonControl implements GLFWMouseButtonCallbackI {
    public static final int PRIMARY = 0, SECONDARY = 1;
    private MouseButton buttons[];

    public MouseButtonControl(long window) {
        glfwSetMouseButtonCallback(window, this);
        buttons = new MouseButton[2];
        for (int i = 0; i < buttons.length; i++)
            buttons[i] = new MouseButton();
    }

    private void setMouseState(int mouse, State state) {
        buttons[mouse].setState(state);
    }

    public boolean isMousePressed(int mouse) {
        return buttons[mouse].getState() == State.PRESSED;
    }

    public boolean isMouseDown(int mouse) {
        return buttons[mouse].getState() == State.DOWN;
    }

    public void next() {
        for (MouseButton button : buttons)
            button.next();
    }

    @Override
    public void invoke(long window, int button, int action, int mods) {
        int mouse = button == GLFW_MOUSE_BUTTON_1 ? PRIMARY : SECONDARY;
        if (action == GLFW_RELEASE)
            setMouseState(mouse, State.RELEASED);
        else if (action == GLFW_PRESS)
            setMouseState(mouse, State.PRESSED);
    }

    private class MouseButton {
        private State state;
        private boolean read;

        private void setState(State state) {
            this.state = state;
            read = false;
        }

        private State getState() {
            read = true;
            return state;
        }

        private void next() {
            if (read)
                if (state == State.PRESSED)
                    state = State.DOWN;
                else if (state == State.RELEASED)
                    state = State.UP;
        }
    }
}   