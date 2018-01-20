package ui;

import character.Crafting;
import character.Equipment;
import character.Human;
import character.gear.Gear;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

class UiCrafting extends UiInteractivePane {
    private static final int TOP_SIZE = Equipment.getGearTypeCount(), BOTTOM_SIZE = Gear.GEAR_MAX_PROPERTIES;
    private Human human;
    private Crafting crafting;
    private UiInventory uiInventory;

    UiCrafting(float[] backColor, Rects rects, Texts texts, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl, Human human, Crafting crafting) {
        super(26, 2, false, Location.RIGHT, backColor, rects, texts, mousePosControl, mouseButtonControl);
        setText(-2, "CRAFTING");
        this.human = human;
        this.crafting = crafting;

        setText(15, "Base");
        setText(16, "Base Reset");
        setText(18, "Primary");
        setText(19, "Primary Reset");
        setText(21, "Secondary");
        setText(22, "Secondary Reset");
        setText(24, "Enhance");
        setText(25, "Enhance Reset");
    }

    void setUiInventory(UiInventory uiInventory) {
        this.uiInventory = uiInventory;
    }

    @Override
    void updateTexts() {
        setText(0, crafting.getText());
        for (int i = 0; i < Gear.GEAR_MAX_PROPERTIES; i++)
            setText(i + 1, crafting.getText(i));

        setText(9, "Earth     " + "Earth II     " + "Earth Fire     " + "Earth Water");
        setText(10, "Fire     " + "Fire II     " + "Fire Air");
        setText(11, "Water     " + "Water II     " + "Water Air     " + "Water Fire");
        setText(12, "Air     " + "Air II     " + "Air Earth");

        //        int highlighted = getHighlighted();
        //        if (highlighted < 15)
        //            highlighted = -1;
        //        setHighlight(highlighted);
        //
        //
        //        if (highlighted >= TOP_SIZE)
        //            highlighted = -1;
        //        setHighlight(highlighted);
        //
        //        if (getClick() == MouseButtonControl.PRIMARY && highlighted != -1) {
        //            int inventorySelected = uiInventory.getSelectedLast();
        //
        //            if (inventorySelected != -1) {
        //                human.swapEquipment(inventorySelected, highlighted);
        //                uiInventory.setSelect(-1);
        //                setSelect(highlighted);
        //            } else if (getSelectedLast() == highlighted)
        //                setSelect(-1);
        //            else
        //                setSelect(highlighted);
        //        }
        //
        //        for (int i = 0; i < TOP_SIZE; i++)
        //            setText(i, equipment.getText(Equipment.getGearType(i)));
        //
        //        int selected = getSelectedLast();
        //
        //        if (selected != -1 && equipment.isEquiped(Equipment.getGearType(selected)))
        //            for (int i = 0; i < BOTTOM_SIZE; i++)
        //                setText(i + TOP_SIZE + 1, equipment.getText(Equipment.getGearType(selected), i));
        //        else
        //            for (int i = 0; i < BOTTOM_SIZE; i++)
        //                setText(i + TOP_SIZE + 1, "");
    }
}