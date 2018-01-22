package ui;

import character.Human;
import character.Inventory;
import character.gear.Gear;
import control.MouseButton;
import control.MouseButtonControl;
import control.MousePosControl;
import item.Item;
import shape.Rects;
import shape.Texts;

class UiInventory extends UiInteractivePane {
    private static final int SIZE = 16; // todo align with inventory.size
    private GearWriter gearWriter;
    private Human human;
    private Inventory inventory;
    private UiEquipment uiEquipment;

    UiInventory(float[] backColor, Rects rects, Texts texts, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl, Human human, Inventory inventory) {
        super(SIZE + GearWriter.SIZE + 1, 2, false, Location.LEFT, backColor, rects, texts, mousePosControl, mouseButtonControl);
        setText(-2, "INVENTORY");
        gearWriter = new GearWriter(this, SIZE + 1, "");
        this.human = human;
        this.inventory = inventory;
    }

    void setUiEquipment(UiEquipment uiEquipment) {
        this.uiEquipment = uiEquipment;
    }

    @Override
    void updateTexts() {
        int highlighted = getHighlighted();
        setHighlight(highlighted);

        Item hoverItem;
        if (highlighted == -1 || (hoverItem = inventory.getItem(highlighted)) == null || !Gear.isGear(hoverItem.id))
            gearWriter.setGear(null);
        else
            gearWriter.setGear((Gear) hoverItem);

        if (getClick() == MouseButton.PRIMARY && highlighted != -1) {
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

        for (int i = 0; i < SIZE; i++)
            setText(i, inventory.getItem(i) == null ? "" : inventory.getItem(i).getText());
    }
}