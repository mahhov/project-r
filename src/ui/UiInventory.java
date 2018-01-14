package ui;

import character.Inventory;
import shape.Rects;
import shape.Texts;

class UiInventory extends UiTextListPane {
    private static final int SIZE = 16;
    private Inventory inventory;

    UiInventory(float left, float top, float right, float bottom, float[] backColor, Rects rects, Texts texts, Inventory inventory) {
        super(SIZE, false, left, top, right, bottom, backColor, rects, texts);
        this.inventory = inventory;
    }

    @Override
    void updateTexts() {
        for (int i = 0; i < size; i++)
            setText(i, inventory.getItem(i) == null ? "" : inventory.getItem(i).print());
    }
}