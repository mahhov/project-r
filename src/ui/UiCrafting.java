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

        setText(0, "Juba");
        setText(1, "Prop 1");
        setText(2, "Prop 2");
        setText(3, "Prop 3");
        setText(4, "Prop 4");
        setText(5, "Prop 5");
        setText(6, "Prop 6");
        setText(7, "Prop 7");
        setText(8 + 1, "Glow 1, Glow 2, Glow 3, Glow 4");
        setText(8 + 2, "Glow 1, Glow 2, Glow 3, Glow 4");
        setText(8 + 3, "Glow 1, Glow 2, Glow 3, Glow 4");
        setText(8 + 4, "Glow 1, Glow 2, Glow 3, Glow 4");
        setText(13, "Clear Glows");
        
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
        //        int highlighted = getHighlighted();
        //        if (highlighted >= TOP_SIZE)
        //            highlighted = -1;
        //        setHighlight(highlighted);
        //
        //        if (getClick() == MouseButtonControl.PRIMARY && highlighted != -1) {
        //            int inventorySelected = uiInventory.getSelected();
        //
        //            if (inventorySelected != -1) {
        //                human.swapEquipment(inventorySelected, highlighted);
        //                uiInventory.setSelect(-1);
        //                setSelect(highlighted);
        //            } else if (getSelected() == highlighted)
        //                setSelect(-1);
        //            else
        //                setSelect(highlighted);
        //        }
        //
        //        for (int i = 0; i < TOP_SIZE; i++)
        //            setText(i, equipment.getText(Equipment.getGearType(i)));
        //
        //        int selected = getSelected();
        //
        //        if (selected != -1 && equipment.isEquiped(Equipment.getGearType(selected)))
        //            for (int i = 0; i < BOTTOM_SIZE; i++)
        //                setText(i + TOP_SIZE + 1, equipment.getText(Equipment.getGearType(selected), i));
        //        else
        //            for (int i = 0; i < BOTTOM_SIZE; i++)
        //                setText(i + TOP_SIZE + 1, "");
    }
}