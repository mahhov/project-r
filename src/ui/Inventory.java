package ui;

import shape.Rects;

class Inventory {
    private Rects.Rect backRect;
    private boolean visible;

    Inventory(float left, float top, float right, float bottom, float[] color, float[] backColor, Rects rects) {
        backRect = rects.addRect(backColor);
        backRect.setCoordinates(left, top, right, bottom);
        backRect.disable();
    }

    void toggle() {
        visible = !visible;
        if (visible)
            backRect.enable();
        else
            backRect.disable();
    }
}