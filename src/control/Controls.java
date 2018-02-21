package control;

public class Controls {
    public KeyControl keyControl;
    public MousePosControl mousePosControl;
    public MouseButtonControl mouseButtonControl;
    public MouseScrollControl mouseScrollControl;

    public Controls(long window) {
        keyControl = new KeyControl(window);
        mousePosControl = new MousePosControl(window);
        mouseButtonControl = new MouseButtonControl(window);
        mouseScrollControl = new MouseScrollControl(window);
    }
}