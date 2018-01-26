package ui;

import character.Crafting;
import character.Glows;
import character.gear.Gear;
import control.MouseButton;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

class UiCrafting extends UiInteractivePane {
    private static final int CRAFTING_TEXTS_OFFSET = Gear.GEAR_MAX_PROPERTIES + 4, SELECTOR_TEXTS_OFFSET = CRAFTING_TEXTS_OFFSET + 12;
    private Crafting crafting;
    private UiGlows uiGlows;
    private GearWriter gearWriter;

    UiCrafting(float[] backColor, Rects rects, Texts texts, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl, Crafting crafting) {
        super(SELECTOR_TEXTS_OFFSET + 2, 2, false, Location.RIGHT, backColor, rects, texts, mousePosControl, mouseButtonControl);
        this.crafting = crafting;

        gearWriter = new GearWriter(this, 0, "--Select Gear to Craft--");

        setText(-2, "CRAFTING");
        setText(CRAFTING_TEXTS_OFFSET, "Base");
        setText(CRAFTING_TEXTS_OFFSET + 1, "Base Reset");
        setText(CRAFTING_TEXTS_OFFSET + 3, "Primary");
        setText(CRAFTING_TEXTS_OFFSET + 4, "Primary Reset");
        setText(CRAFTING_TEXTS_OFFSET + 6, "Secondary");
        setText(CRAFTING_TEXTS_OFFSET + 7, "Secondary Reset");
        setText(CRAFTING_TEXTS_OFFSET + 9, "Enhance");
        setText(CRAFTING_TEXTS_OFFSET + 10, "Enhance Reset");

        setText(SELECTOR_TEXTS_OFFSET, "Prev Item");
        setText(SELECTOR_TEXTS_OFFSET + 1, "Next Item");
    }

    void setUiGlow(UiGlows uiGlows) {
        this.uiGlows = uiGlows;
    }

    @Override
    void updateTexts() {
        int highlighted = getHighlighted();
        if (highlighted < CRAFTING_TEXTS_OFFSET
                || highlighted == CRAFTING_TEXTS_OFFSET + 2
                || highlighted == CRAFTING_TEXTS_OFFSET + 5
                || highlighted == CRAFTING_TEXTS_OFFSET + 8
                || highlighted == CRAFTING_TEXTS_OFFSET + 11)
            highlighted = -1;

        setHighlight(highlighted);

        if (getClick() == MouseButton.PRIMARY && highlighted != -1) {
            Glows.Glow[] glowsSelected = uiGlows.getGlowsSelected();
            boolean crafted = true;

            if (highlighted == CRAFTING_TEXTS_OFFSET)
                crafting.craftBase(glowsSelected);
            else if (highlighted == CRAFTING_TEXTS_OFFSET + 1)
                crafting.resetBase();

            else if (highlighted == CRAFTING_TEXTS_OFFSET + 3)
                crafting.craftPrimary(glowsSelected);
            else if (highlighted == CRAFTING_TEXTS_OFFSET + 4)
                crafting.resetPrimary();

            else if (highlighted == CRAFTING_TEXTS_OFFSET + 6)
                crafting.craftSecondary(glowsSelected);
            else if (highlighted == CRAFTING_TEXTS_OFFSET + 7)
                crafting.resetSecondary();

            else if (highlighted == CRAFTING_TEXTS_OFFSET + 9)
                crafting.craftEnhance();
            else if (highlighted == CRAFTING_TEXTS_OFFSET + 10)
                crafting.resetEnhance();

            else
                crafted = false;

            if (crafted)
                uiGlows.refreshSelectedGlows();

            if (highlighted == SELECTOR_TEXTS_OFFSET) {
                crafting.selectInventoryGear(-1);
                gearWriter.setGear(crafting.getGear());
            } else if (highlighted == SELECTOR_TEXTS_OFFSET + 1) {
                crafting.selectInventoryGear(1);
                gearWriter.setGear(crafting.getGear());
            } else
                gearWriter.refreshText();
        }
    }
}