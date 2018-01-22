package ui;

import character.Log;
import shape.Rects;
import shape.Texts;

class UiTextBox extends UiPane {
    private static final int SIZE = 8;
    private Log log;

    UiTextBox(float[] backColor, Rects rects, Texts texts, Log log) {
        super(SIZE, 0, true, Location.BOTTOM, backColor, rects, texts);
        this.log = log;
    }

    @Override
    void updateTexts() {
        int i = 0;
        for (String text : log.getLogQueue()) {
            setText(i, text);
            if (++i == size)
                break;
        }
    }
}