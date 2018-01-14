package control;

import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class MouseButtonControl implements GLFWMouseButtonCallbackI {
    private static final int UP = 0, DOWN = 1, PRESSED = 2, RELEASED = 3;
    public static final int PRIMARY = 0, SECONDARY = 1;
    private int buttons[];

    public MouseButtonControl(long window) {
        glfwSetMouseButtonCallback(window, this);
        buttons = new int[2];
    }

    private void setMouseState(int mouse, int state) {
        buttons[mouse] = state;
    }

    public boolean isMousePreseed(int mouse) {
        if (buttons[mouse] == PRESSED) {
            buttons[mouse] = DOWN;
            return true;
        }
        return false;
    }

    public boolean isMouseDown(int mouse) {
        if (buttons[mouse] == PRESSED)
            buttons[mouse] = DOWN;
        return buttons[mouse] == DOWN;
    }

    @Override
    public void invoke(long window, int button, int action, int mods) {
        int mouse = button == GLFW_MOUSE_BUTTON_1 ? PRIMARY : SECONDARY;
        if (action == GLFW_RELEASE)
            setMouseState(mouse, RELEASED);
        else if (action == GLFW_PRESS)
            setMouseState(mouse, PRESSED);
    }
}   