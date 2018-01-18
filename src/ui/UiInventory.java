package ui;

import character.Inventory;
import shape.Rects;
import shape.Texts;

class UiInventory extends UiPane {
    private static final int SIZE = 16;
    private Inventory inventory;

    UiInventory(float[] backColor, Rects rects, Texts texts, Inventory inventory) {
        super(SIZE, 2,false, Location.LEFT, backColor, rects, texts);
        setText(-2, "INVENTORY");
        this.inventory = inventory;
    }

    @Override
    void updateTexts() {
        for (int i = 0; i < size; i++)
            setText(i, inventory.getItem(i) == null ? "" : inventory.getItem(i).getText());
    }
}