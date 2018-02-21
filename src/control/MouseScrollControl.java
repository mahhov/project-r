package control;

import org.lwjgl.glfw.GLFWScrollCallbackI;

import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;

public class MouseScrollControl implements GLFWScrollCallbackI {
    int scroll, scrollX;

    public MouseScrollControl(long window) {
        glfwSetScrollCallback(window, this);
    }

    public int getScroll() {
        int scroll = this.scroll;
        this.scroll = 0;
        return scroll;
    }

    public int getScrollX() {
        int scrollX = this.scrollX;
        this.scrollX = 0;
        return scrollX;
    }

    @Override
    public void invoke(long window, double xoffset, double yoffset) {
        if (yoffset > 0)
            scroll++;
        else if (yoffset < 0)
            scroll--;

        if (xoffset > 0)
            scrollX++;
        else if (xoffset < 0)
            scrollX--;
    }
}   