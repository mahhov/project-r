package ui;

import character.Human;
import character.Inventory;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

class UiInventory extends UiInteractivePane {
    private static final int SIZE = 16;
    private Human human;
    private Inventory inventory;
    private UiEquipment uiEquipment;
    private UiCrafting uiCrafting;

    UiInventory(float[] backColor, Rects rects, Texts texts, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl, Human human, Inventory inventory) {
        super(SIZE, 2, false, Location.LEFT, backColor, rects, texts, mousePosControl, mouseButtonControl);
        setText(-2, "INVENTORY");
        this.human = human;
        this.inventory = inventory;
    }

    void setUiEquipment(UiEquipment uiEquipment) {
        this.uiEquipment = uiEquipment;
    }
    
    void setUiCrafting(UiCrafting uiCrafting) {
        this.uiCrafting = uiCrafting;
    }

    @Override
    void updateTexts() {
        int highlighted = getHighlighted();
        setHighlight(highlighted);

        if (getClick() == MouseButtonControl.PRIMARY && highlighted != -1) {
            int selected = getSelectedLast();
            int equipmentSelected = uiEquipment.getSelectedLast();

            if (selected != -1) {
                inventory.swap(selected, highlighted);
                setSelect(-1);
            } else if (equipmentSelected != -1) {
                human.swapEquipment(highlighted, equipmentSelected);
                uiEquipment.setSelect(-1);
                setSelect(highlighted);
            } else if (inventory.getItem(highlighted) == null)
                setSelect(-1);
            else
                setSelect(highlighted);
        }

        for (int i = 0; i < size; i++)
            setText(i, inventory.getItem(i) == null ? "" : inventory.getItem(i).getText());
    }
}