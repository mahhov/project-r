package ui;

import character.Equipment;
import character.Human;
import control.MouseButton;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

class UiEquipment extends UiInteractivePane {
    private static final int TOP_GEAR_SIZE = Equipment.getGearTypeCount(), TOP_MODULE_SIZE = Equipment.MODULE_COUNT, TOP_SIZE = TOP_GEAR_SIZE + TOP_MODULE_SIZE;
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

        if (highlighted == -1)
            gearWriter.setGear(null);
        else if (highlighted < TOP_GEAR_SIZE)
            gearWriter.setGear(equipment.getGear(Equipment.getGearType(highlighted)));
        else
            gearWriter.setGear(equipment.getModule(highlighted - TOP_GEAR_SIZE));

        if (getClick() == MouseButton.PRIMARY && highlighted != -1) {
            int inventorySelected = uiInventory.getSelectedLast();

            if (inventorySelected != -1) {
                if (highlighted < TOP_GEAR_SIZE)
                    human.swapEquipment(inventorySelected, highlighted);
                else
                    human.swapEquipmentModule(inventorySelected, highlighted - TOP_GEAR_SIZE);
                uiInventory.setSelect(-1);
                setSelect(highlighted);
            } else if (getSelectedLast() == highlighted)
                setSelect(-1);
            else
                setSelect(highlighted);
        }

        for (int i = 0; i < TOP_GEAR_SIZE; i++)
            setText(i, equipment.getGearText(Equipment.getGearType(i)));

        for (int i = TOP_GEAR_SIZE; i < TOP_SIZE; i++)
            setText(i, equipment.getModuleText(i - TOP_GEAR_SIZE));
    }
}