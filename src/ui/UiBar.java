package ui;

import shape.Rects;
import util.MathNumbers;

class UiBar {
    private static final float PERCENT_WEIGHT = .1f;
    private float percent;

    private static final float[] BORDER_COLOR = {0, 0, 0, 1f};
    private static final float BORDER = .003f;
    private static final float RECT_RIGHT_BUFFER = 1 / 3f;
    private static final float COLOR_MAX_DARKEN = .75f;
    private int numRects = 5;
    private float[] rectEdges;
    private Rects.Rect[] rects;

    private float left, top, width, bottom;
    private Rects.Rect backRect;

    UiBar(float left, float top, float right, float bottom, float[] color, float[] backColor, Rects rects) {
        this.left = left;
        this.top = top;
        width = right - left;
        this.bottom = bottom;

        rectEdges = new float[numRects];
        float rectsWidth = width * (1 - RECT_RIGHT_BUFFER) / numRects;
        for (int i = 1; i < numRects; i++)
            rectEdges[i] = left + rectsWidth * (numRects - i);
        rectEdges[0] = right;

        backRect = rects.addRect(BORDER_COLOR);
        backRect.setCoordinates(left - BORDER, top + BORDER, right + BORDER, bottom - BORDER);

        this.rects = new Rects.Rect[numRects];
        for (int i = 0; i < numRects; i++)
            this.rects[i] = rects.addRect(createColor(color, i * (COLOR_MAX_DARKEN / numRects)));
    }

    private float[] createColor(float[] origColor, float darken) {
        float color[] = new float[4];
        float weight = 1 - darken;
        color[0] = origColor[0] * weight;
        color[1] = origColor[1] * weight;
        color[2] = origColor[2] * weight;
        color[3] = origColor[3];
        return color;
    }

    void setPercentFill(float percent) {
        this.percent += (percent - this.percent) * PERCENT_WEIGHT;

        float right = left + width * this.percent;
        for (int i = 0; i < numRects; i++)
            rects[i].setCoordinates(left, top, MathNumbers.min(rectEdges[i], right), bottom);
    }

    void hide() {
        for (int i = 0; i < numRects; i++)
            rects[i].disable();
        backRect.disable();
    }

    void show() {
        for (int i = 0; i < numRects; i++)
            rects[i].enable();
        backRect.enable();
    }
}