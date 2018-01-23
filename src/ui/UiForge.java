package ui;

import character.Equipment;
import character.Forge;
import control.MouseButton;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

class UiForge extends UiInteractivePane {
    private static final int GEAR_OFFSET = 2;
    private Forge forge;

    UiForge(float[] backColor, Rects rects, Texts texts, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl, Forge forge) {
        super(Equipment.getGearTypeCount() + GEAR_OFFSET, 2, false, Location.RIGHT, backColor, rects, texts, mousePosControl, mouseButtonControl);
        this.forge = forge;

        setText(-2, "FORGE");
        for (int i = GEAR_OFFSET; i < size; i++)
            setText(i, forge.getText(Equipment.getGearType(i - GEAR_OFFSET)));
    }

    @Override
    void updateTexts() {
        int highlighted = getHighlighted();
        if (highlighted < GEAR_OFFSET || !forge.canForge(Equipment.getGearType(highlighted - GEAR_OFFSET)))
            highlighted = -1;
        setHighlight(highlighted);

        if (getClick() == MouseButton.PRIMARY && highlighted != -1)
            forge.forge(Equipment.getGearType(highlighted - GEAR_OFFSET));

        setText(0, "Metal " + forge.getMetal());
    }
}