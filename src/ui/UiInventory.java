package ui;

import character.Inventory;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

class UiInventory extends UiInteractivePane {
    private static final int SIZE = 16;
    private Inventory inventory;

    UiInventory(float[] backColor, Rects rects, Texts texts, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl, Inventory inventory) {
        super(SIZE, 2, false, Location.LEFT, backColor, rects, texts, mousePosControl, mouseButtonControl);
        setText(-2, "INVENTORY");
        this.inventory = inventory;
    }

    @Override
    void updateTexts() {
        int highlighted = getHighlighted();
        setHighlight(highlighted);

        if (getClick() == MouseButtonControl.PRIMARY) {
            int selected = getSelected();
            if (selected == -1) {
                if (highlighted != -1 && inventory.getItem(highlighted) != null)
                    setSelect(highlighted);
            } else {
                if (highlighted != -1)
                    inventory.swap(selected, highlighted);
                setSelect(-1);
            }
        }

        for (int i = 0; i < size; i++)
            setText(i, inventory.getItem(i) == null ? "" : inventory.getItem(i).getText());
    }
}