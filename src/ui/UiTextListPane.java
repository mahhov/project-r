package ui;

import shape.Rects;
import shape.Texts;

abstract class UiTextListPane {
    private static final float[] HIGHLIGHT_COLOR = new float[] {0, 1, 0, 1};
    private static final float MARGIN = 0.01f;
    final int size;
    private float itemLeft, itemTop, itemRight, itemBottom, itemOffsetY, itemHeight, itemHeightRatio;
    private Rects.Rect backRect;
    private Texts.Text texts[];
    private int highlighted;

    private boolean visible;

    UiTextListPane(int size, boolean visible, float left, float top, float right, float bottom, float[] backColor, Rects rects, Texts texts) {
        this.size = size;
        this.visible = !visible;

        backRect = rects.addRect(backColor);
        backRect.setCoordinates(left, top, right, bottom);

        itemLeft = left + MARGIN;
        itemTop = top - MARGIN;
        itemRight = right - MARGIN;
        itemBottom = bottom + MARGIN;
        itemOffsetY = (itemTop - bottom) / size;
        itemHeight = itemOffsetY - MARGIN;
        itemHeightRatio = itemHeight / itemOffsetY;
        this.texts = new Texts.Text[size];
        for (int i = 0; i < size; i++) {
            this.texts[i] = texts.addText();
            float topOffset = itemTop - itemOffsetY * i;
            this.texts[i].setCoordinates(itemLeft, topOffset, topOffset - itemHeight);
        }

        toggle();
    }

    void toggle() {
        visible = !visible;
        if (visible) {
            setVisible();
        } else {
            setInvisible();
        }
    }

    void setVisible() {
        backRect.enable();
        for (Texts.Text text : texts)
            text.enable();
    }

    void setInvisible() {
        backRect.disable();
        for (Texts.Text text : texts)
            text.disable();
    }

    void setText(int i, String text) {
        texts[i].setText(text);
    }

    void setHighlight(int i) {
        if (highlighted != -1)
            texts[highlighted].setColor(null);
        highlighted = i;
        if (highlighted != -1)
            texts[highlighted].setColor(HIGHLIGHT_COLOR);
    }

    void update() {
        if (visible)
            updateTexts();
    }

    int getIntersecting(float x, float y) {
        if (x < itemLeft || x > itemRight || y > itemTop || y < itemBottom)
            return -1;
        float shiftedY = (itemTop - y) / itemOffsetY;
        int intShiftedY = (int) shiftedY;
        return shiftedY - intShiftedY > itemHeightRatio ? -1 : intShiftedY;
    }

    abstract void updateTexts();
}