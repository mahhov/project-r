package ui;

import shape.Rects;
import shape.Texts;

abstract class UiTextListPane {
    private static final float MARGIN = 0.01f;
    final int size;
    private Rects.Rect backRect;
    private Texts.Text texts[];

    private boolean visible;

    UiTextListPane(int size, boolean visible, float left, float top, float right, float bottom, float[] backColor, Rects rects, Texts texts) {
        this.size = size;
        this.visible = !visible;

        backRect = rects.addRect(backColor);
        backRect.setCoordinates(left, top, right, bottom);

        float height = (top - bottom - MARGIN) / size - MARGIN;
        this.texts = new Texts.Text[size];
        for (int i = 0; i < size; i++) {
            this.texts[i] = texts.addText();
            float topOffset = top - MARGIN - (MARGIN + height) * i;
            this.texts[i].setCoordinates(left + MARGIN, topOffset, topOffset - height);
        }

        toggle();
    }

    void toggle() {
        visible = !visible;
        if (visible) {
            backRect.enable();
            for (Texts.Text text : texts)
                text.enable();
        } else {
            backRect.disable();
            for (Texts.Text text : texts)
                text.disable();
        }
    }

    void setText(int i, String text) {
        texts[i].setText(text);
    }

    void update() {
        if (visible)
            updateTexts();
    }

    abstract void updateTexts();
}