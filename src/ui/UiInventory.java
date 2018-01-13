package ui;

import character.Inventory;
import shape.Rects;
import shape.Texts;

class UiInventory {
    private static final int SIZE = 16;
    private static final float MARGIN = 0.01f;
    private Rects.Rect backRect;
    private Texts.Text itemTexts[];

    private boolean visible;
    private Inventory inventory;

    UiInventory(float left, float top, float right, float bottom, float[] backColor, Rects rects, Texts texts, Inventory inventory) {
        backRect = rects.addRect(backColor);
        backRect.setCoordinates(left, top, right, bottom);

        float height = (top - bottom - MARGIN) / SIZE - MARGIN;
        itemTexts = new Texts.Text[SIZE];
        for (int i = 0; i < SIZE; i++) {
            itemTexts[i] = texts.addText();
            float topOffset = top - MARGIN - (MARGIN + height) * i;
            itemTexts[i].setCoordinates(left + MARGIN, topOffset, topOffset - height);
        }

        this.inventory = inventory;

        visible = true;
        toggle();
    }

    void toggle() {
        visible = !visible;
        if (visible) {
            backRect.enable();
            for (Texts.Text text : itemTexts)
                text.enable();
        } else {
            backRect.disable();
            for (Texts.Text text : itemTexts)
                text.disable();
        }
    }

    void update() {
        if (visible)
            for (int i = 0; i < SIZE; i++)
                itemTexts[i].setText(inventory.getItem(i) == null ? "" : inventory.getItem(i).print());
    }
}