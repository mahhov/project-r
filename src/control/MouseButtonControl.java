package control;

import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class MouseButtonControl implements GLFWMouseButtonCallbackI {
    private static final int UP = 0, DOWN = 1, PRESSED = 2, RELEASED = 3;
    public static final int PRIMARY = 0, SECONDARY = 1;
    private MouseButton buttons[];

    public MouseButtonControl(long window) {
        glfwSetMouseButtonCallback(window, this);
        buttons = new MouseButton[2];
        for (int i = 0; i < buttons.length; i++)
            buttons[i] = new MouseButton();
    }

    private void setMouseState(int mouse, int state) {
        buttons[mouse].setState(state);
    }

    public boolean isMousePressed(int mouse) {
        return buttons[mouse].getState() == PRESSED;
    }

    public boolean isMouseDown(int mouse) {
        return buttons[mouse].getState() == DOWN;
    }

    public void next() {
        for (MouseButton button : buttons)
            button.next();
    }

    @Override
    public void invoke(long window, int button, int action, int mods) {
        int mouse = button == GLFW_MOUSE_BUTTON_1 ? PRIMARY : SECONDARY;
        if (action == GLFW_RELEASE)
            setMouseState(mouse, RELEASED);
        else if (action == GLFW_PRESS)
            setMouseState(mouse, PRESSED);
    }

    private class MouseButton {
        private int state;
        private boolean read;

        private void setState(int state) {
            this.state = state;
            read = false;
        }

        private int getState() {
            read = true;
            return state;
        }

        private void next() {
            if (read)
                if (state == PRESSED)
                    state = DOWN;
                else if (state == RELEASED)
                    state = UP;
        }
    }
}   