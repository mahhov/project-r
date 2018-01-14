package ui;

import shape.Rects;
import shape.Texts;

class UiTextBox extends UiTextListPane {
    private static final int SIZE = 8;
    private TextSystem textSystem;

    UiTextBox(float left, float top, float right, float bottom, float[] backColor, Rects rects, Texts texts, TextSystem textSystem) {
        super(SIZE, true, left, top, right, bottom, backColor, rects, texts);
        this.textSystem = textSystem;
    }

    @Override
    void updateTexts() {
        int i = 0;
        for (String text : textSystem.getTexts()) {
            setText(i, text);
            if (++i == size)
                break;
        }
    }
}