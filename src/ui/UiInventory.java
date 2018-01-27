package ui;

import character.Equipment;
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
    private Human human;
    private Inventory inventory;
    private UiEquipment uiEquipment;
    private GearWriter gearWriter;

    UiInventory(float[] backColor, Rects rects, Texts texts, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl, Human human, Inventory inventory) {
        super(SIZE + GearWriter.SIZE + 1, 2, false, Location.LEFT, backColor, rects, texts, mousePosControl, mouseButtonControl);
        this.human = human;
        this.inventory = inventory;
        gearWriter = new GearWriter(this, SIZE + 1, "");
        setText(-2, "INVENTORY");
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
                if (equipmentSelected < Equipment.getGearTypeCount())
                    human.swapEquipment(highlighted, equipmentSelected);
                else
                    human.swapEquipmentModule(highlighted, equipmentSelected - Equipment.getGearTypeCount());
                uiEquipment.setSelect(-1);
                if (inventory.getItem(highlighted) != null)
                    setSelect(highlighted);
            } else if (inventory.getItem(highlighted) != null)
                setSelect(highlighted);
        }

        for (int i = 0; i < SIZE; i++)
            setText(i, inventory.getItem(i) == null ? "" : inventory.getItem(i).getText());
    }
}