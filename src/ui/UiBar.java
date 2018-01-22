package ui;

import shape.Rects;

class UiBar {
    private static final float PERCENT_WEIGHT = .1f;
    private float percent;

    private float left, top, width, bottom;
    private Rects.Rect rect, backRect;

    UiBar(float left, float top, float right, float bottom, float[] color, float[] backColor, Rects rects) {
        this.left = left;
        this.top = top;
        width = right - left;
        this.bottom = bottom;

        backRect = rects.addRect(backColor);
        backRect.setCoordinates(left, top, right, bottom);
        rect = rects.addRect(color);
    }

    void setPercentFill(float percent) {
        this.percent += (percent - this.percent) * PERCENT_WEIGHT;
        rect.setCoordinates(left, top, left + width * this.percent, bottom);
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