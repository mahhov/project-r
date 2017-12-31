package control;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class MousePosControl implements GLFWCursorPosCallbackI {
    private double x, y;
    private boolean ready;

    public MousePosControl(long window) {
        glfwSetCursorPosCallback(window, this);
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public float getX() {
        float x = (float) this.x;
        this.x = 0;
        return x;
    }

    public float getY() {
        float y = (float) this.y;
        this.y = 0;
        return y;
    }

    @Override
    public void invoke(long window, double xpos, double ypos) {
        if (ready) {
            x += xpos;
            y += ypos;
        } else
            ready = true;
        glfwSetCursorPos(window, 0, 0);
    }
}   