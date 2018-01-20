package ui;

import character.Equipment;
import character.Human;
import character.gear.Gear;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

class UiEquipment extends UiInteractivePane {
    private static final int TOP_SIZE = Equipment.getGearTypeCount(), BOTTOM_SIZE = Gear.GEAR_MAX_PROPERTIES;
    private Human human;
    private Equipment equipment;
    private UiInventory uiInventory;

    UiEquipment(float[] backColor, Rects rects, Texts texts, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl, Human human, Equipment equipment) {
        super(TOP_SIZE + BOTTOM_SIZE + 1, 2, false, Location.RIGHT, backColor, rects, texts, mousePosControl, mouseButtonControl);
        setText(-2, "EQUIPMENT");
        this.human = human;
        this.equipment = equipment;
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

        if (getClick() == MouseButtonControl.PRIMARY && highlighted != -1) {
            int inventorySelected = uiInventory.getSelectedLast();

            if (inventorySelected != -1) {
                human.swapEquipment(inventorySelected, highlighted);
                uiInventory.setSelect(-1);
                setSelect(highlighted);
            } else if (getSelectedLast() == highlighted)
                setSelect(-1);
            else
                setSelect(highlighted);
        }

        for (int i = 0; i < TOP_SIZE; i++)
            setText(i, equipment.getText(Equipment.getGearType(i)));

        int selected = getSelectedLast();

        if (selected != -1 && equipment.isEquiped(Equipment.getGearType(selected)))
            for (int i = 0; i < BOTTOM_SIZE; i++)
                setText(i + TOP_SIZE + 1, equipment.getText(Equipment.getGearType(selected), i));
        else
            for (int i = 0; i < BOTTOM_SIZE; i++)
                setText(i + TOP_SIZE + 1, "");
    }
}