package ui;

import character.Equipment;
import character.Human;
import control.MouseButton;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

class UiEquipment extends UiInteractivePane {
    private static final int TOP_SIZE = Equipment.getGearTypeCount();
    private Human human;
    private Equipment equipment;
    private UiInventory uiInventory;
    private GearWriter gearWriter;

    UiEquipment(float[] backColor, Rects rects, Texts texts, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl, Human human, Equipment equipment) {
        super(TOP_SIZE + GearWriter.SIZE + 1, 2, false, Location.RIGHT, backColor, rects, texts, mousePosControl, mouseButtonControl);
        this.human = human;
        this.equipment = equipment;
        gearWriter = new GearWriter(this, TOP_SIZE + 1, "");
        setText(-2, "EQUIPMENT");
    }

    void setUiInventory(UiInventory uiInventory) {
        this.uiInventory = uiInventory;
    }

    @Override
    void updateTexts() {
        int highlighted = getHighlighted();
        if (highlighted >= TOP_SIZE)
            highlighted = -1;
        setHighlight(highlighted);

        if (getClick() == MouseButton.PRIMARY && highlighted != -1) {
            int inventorySelected = uiInventory.getSelectedLast();

            if (inventorySelected != -1) {
                human.swapEquipment(inventorySelected, highlighted);
                uiInventory.setSelect(-1);
                setSelect(highlighted);
                gearWriter.setGear(equipment.getGear(Equipment.getGearType(highlighted)));
            } else if (getSelectedLast() == highlighted) {
                setSelect(-1);
                gearWriter.setGear(null);
            } else {
                setSelect(highlighted);
                gearWriter.setGear(equipment.getGear(Equipment.getGearType(highlighted)));
            }
        }

        for (int i = 0; i < TOP_SIZE; i++)
            setText(i, equipment.getText(Equipment.getGearType(i)));
    }
}