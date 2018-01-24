package ui;

import shape.Rects;
import util.MathNumbers;
import util.MathUtil;

class UiBar {
    private static final float[] BORDER_COLOR = {1, 1, 1, 1};
    private static final float BORDER = .002f;
    private static final int NUM_RECTS = 6;
    private static float MIN_COLOR_MULT = .5f;

    private static final float PERCENT_WEIGHT = .1f;
    private float percent;

    private float[] color, minColor;
    private Rects.Rect backRect;
    private Rects.Rect[] rects;

    UiBar(float left, float top, float right, float bottom, float[] color, float[] backColor, Rects rects) {
        this.color = color;
        this.minColor = MathUtil.colorMult(color, MIN_COLOR_MULT);

        backRect = rects.addRect(BORDER_COLOR);
        backRect.setCoordinates(left - BORDER, top + BORDER, right + BORDER, bottom - BORDER);

        this.rects = new Rects.Rect[NUM_RECTS];
        float w = (right - left) / NUM_RECTS;
        for (int i = 0; i < NUM_RECTS; i++) {
            this.rects[i] = rects.addRect(color);
            float offset = left + w * i;
            this.rects[i].setCoordinates(offset, top, offset + w, bottom);
        }
    }

    void setPercentFill(float percent) {
        this.percent += (percent - this.percent) * PERCENT_WEIGHT;

        float fill = this.percent * NUM_RECTS;
        int i;

        for (i = 0; i < (int) fill; i++)
            rects[i].setColor(color);

        if (i < NUM_RECTS) {
            float mult = MathNumbers.maxMin(fill - i, 1, 0);
            mult = mult * (1 - MIN_COLOR_MULT) + MIN_COLOR_MULT;
            rects[i].setColor(MathUtil.colorMult(color, mult));

            for (i++; i < NUM_RECTS; i++)
                rects[i].setColor(minColor);
        }
    }

    void hide() {
        for (int i = 0; i < NUM_RECTS; i++)
            rects[i].disable();
        backRect.disable();
    }

    void show() {
        for (int i = 0; i < NUM_RECTS; i++)
            rects[i].enable();
        backRect.enable();
    }
}