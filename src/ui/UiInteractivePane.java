package ui;

import control.MouseButton;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

abstract class UiInteractivePane extends UiPane {
    private MousePosControl mousePosControl;
    private MouseButtonControl mouseButtonControl;

    UiInteractivePane(int size, int offset, boolean visible, Location location, float[] backColor, Rects rects, Texts texts, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        super(size, offset, visible, location, backColor, rects, texts);
        this.mousePosControl = mousePosControl;
        this.mouseButtonControl = mouseButtonControl;
    }

    @Override
    void setVisible() {
        super.setVisible();
        if (mousePosControl != null)
            mousePosControl.unlock();
    }

    @Override
    void setInvisible() {
        super.setInvisible();
        if (mousePosControl != null)
            mousePosControl.lock();
        setSelect(-1);
    }

    int getHighlighted() {
        return getIntersecting(mousePosControl.getAbsX(), mousePosControl.getAbsY());
    }

    MouseButton getClick() {
        if (mouseButtonControl.isMousePressed(MouseButton.SECONDARY))
            return MouseButton.SECONDARY;
        else if (mouseButtonControl.isMousePressed(MouseButton.PRIMARY))
            return MouseButton.PRIMARY;
        else
            return MouseButton.NONE;
    }
}