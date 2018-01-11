package ui;

import shape.Rects;

class Bar {
    private float left, top, width, bottom;
    private Rects.Rect rect, backRect;

    Bar(float left, float top, float right, float bottom, float[] color, float[] backColor, Rects rects) {
        this.left = left;
        this.top = top;
        width = right - left;
        this.bottom = bottom;

        rect = rects.addRect(color);
        backRect = rects.addRect(backColor);
        backRect.setCoordinates(left, top, right, bottom);
    }

    void setPercentFill(float percent) {
        rect.setCoordinates(left, top, left + width * percent, bottom);
    }

    void hide() {
        rect.disable();
        backRect.disable();
    }

    void show() {
        rect.enable();
        backRect.enable();
    }
}