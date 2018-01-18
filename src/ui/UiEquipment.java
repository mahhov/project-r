package ui;

import character.Equipment;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

class UiEquipment extends UiInteractivePane {
    private Equipment equipment;

    UiEquipment(float[] backColor, Rects rects, Texts texts, Equipment equipment, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        super(Equipment.getGearTypeCount(), 2, false, Location.RIGHT, backColor, rects, texts, mousePosControl, mouseButtonControl);
        setText(-2, "EQUIPMENT");
        this.equipment = equipment;
    }

    @Override
    void updateTexts() {
        int selected = getSelected();

        setHighlight(selected);

        for (int i = 0; i < size; i++)
            setText(i, equipment.getText(Equipment.getGearType(i)));
    }
}