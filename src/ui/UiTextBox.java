package ui;

import shape.Rects;
import shape.Texts;

class UiTextBox extends UiPane {
    private static final int SIZE = 8;
    private TextSystem textSystem;

    UiTextBox(float[] backColor, Rects rects, Texts texts, TextSystem textSystem) {
        super(SIZE, true, Location.BOTTOM, backColor, rects, texts);
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