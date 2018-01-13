package ui;

import shape.Rects;
import shape.Texts;

public class UiTextBox {
    private static final int SIZE = 8;
    private static final float MARGIN = 0.01f;
    private Rects.Rect backRect; // todo make semi transparant (also for ui inventory)
    private Texts.Text texts[];

    private boolean visible;
    private TextSystem textSystem;

    UiTextBox(float left, float top, float right, float bottom, float[] backColor, Rects rects, Texts texts, TextSystem textSystem) {
        backRect = rects.addRect(backColor);
        backRect.setCoordinates(left, top, right, bottom);
        backRect.disable();

        float height = (top - bottom - MARGIN) / SIZE - MARGIN;

        this.texts = new Texts.Text[SIZE];
        for (int i = 0; i < SIZE; i++) {
            this.texts[i] = texts.addText();
            float topOffset = top - MARGIN - (MARGIN + height) * i;
            this.texts[i].setCoordinates(left + MARGIN, topOffset, topOffset - height);
        }

        this.textSystem = textSystem;

        visible = true;
        toggle();
    }

    void toggle() { // todo reuse uiinventory
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

    void update() {
        if (visible) {
            int i = 0;
            for (String text : textSystem.getTexts()) {
                texts[i++].setText(text);
                if (i == texts.length)
                    break;
            }
        }
    }
}