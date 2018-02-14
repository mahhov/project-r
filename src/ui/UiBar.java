package ui;

import shape.Rects;
import util.math.MathNumbers;
import util.math.MathUtil;

class UiBar {
    private static final float[] BORDER_COLOR = {0, 0, 0, .5f};
    static final float BORDER = .002f * 4;
    private static final float LINE_BORDER = .002f * 1;
    private static final int NUM_RECTS = 6;
    private static float MIN_COLOR_MULT = .5f;

    private static final float PERCENT_WEIGHT = .1f;
    private float percent;

    private float[] color, minColor;
    private Rects.Rect backRect;
    private Rects.Rect[] rects;

    UiBar(float left, float top, float right, float bottom, float[] color, float[] backColor, Rects rects) {
        this.color = color;
        minColor = MathUtil.colorMult(color, MIN_COLOR_MULT);

        backRect = rects.addRect(BORDER_COLOR);
        backRect.setCoordinates(left - BORDER + LINE_BORDER, top + BORDER, right + BORDER - LINE_BORDER, bottom - BORDER);

        this.rects = new Rects.Rect[NUM_RECTS];
        float w = (right - left) / NUM_RECTS;
        for (int i = 0; i < NUM_RECTS; i++) {
            this.rects[i] = rects.addRect(color);
            float offset = left + w * i;
            this.rects[i].setCoordinates(offset + LINE_BORDER, top, offset + w - LINE_BORDER, bottom);
        }
    }

    void setPercentFill(float percent) {
        this.percent += (percent - this.percent) * PERCENT_WEIGHT;

        float fill = this.percent * NUM_RECTS;
        int i;

        for (i = 0; i < (int) fill; i++)
            rects[i].setColor(color);

        if (i < NUM_RECTS) {
            float mult = MathNumbers.minMax(fill - i, 0, 1);
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