package control;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class MousePosControl implements GLFWCursorPosCallbackI {
    private long window;
    private float halfWindowWidth, negHalfWindowHeight;
    private float x, y;
    private boolean ready, locked;

    public MousePosControl(long window) {
        this.window = window;
        int[] width = new int[1], height = new int[1];
        glfwGetWindowSize(window, width, height);
        halfWindowWidth = width[0] / 2;
        negHalfWindowHeight = -height[0] / 2;
        glfwSetCursorPosCallback(window, this);
        lock();
    }

    public float getMoveX() {
        if (!locked)
            return 0;
        float x = this.x;
        this.x = 0;
        return x;
    }

    public float getMoveY() {
        if (!locked)
            return 0;
        float y = this.y;
        this.y = 0;
        return y;
    }

    public float getAbsX() {
        return x;
    }

    public float getAbsY() {
        return y;
    }

    public void lock() {
        locked = true;
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glfwSetCursorPos(window, 0, 0);
        x = 0;
        y = 0;
    }

    public void unlock() {
        locked = false;
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    @Override
    public void invoke(long window, double xpos, double ypos) {
        if (locked)
            invokeLocked(window, (float) xpos, (float) ypos);
        else
            invokeUnlocked((float) xpos, (float) ypos);
    }

    private void invokeLocked(long window, float xpos, float ypos) {
        if (ready) {
            x += xpos;
            y += ypos;
        } else
            ready = true;
        glfwSetCursorPos(window, 0, 0);
    }

    private void invokeUnlocked(float xpos, float ypos) {
        x = xpos / halfWindowWidth - 1;
        y = ypos / negHalfWindowHeight + 1;
    }
}   