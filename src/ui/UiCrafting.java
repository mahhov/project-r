package ui;

import character.Crafting;
import character.Glows;
import character.Human;
import character.gear.Gear;
import control.MouseButton;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

class UiCrafting extends UiInteractivePane {
    private static final int CRAFTING_TEXTS_OFFSET = Gear.GEAR_MAX_PROPERTIES + 4, CRAFTING_TEXTS_HINT = CRAFTING_TEXTS_OFFSET + 12, CRAFTING_TEXTS_BUTTONS_OFFSET = CRAFTING_TEXTS_HINT + 2;
    private Human human;
    private Crafting crafting;
    private UiGlows uiGlows;

    UiCrafting(float[] backColor, Rects rects, Texts texts, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl, Human human, Crafting crafting) {
        super(CRAFTING_TEXTS_BUTTONS_OFFSET + 2, 2, false, Location.RIGHT, backColor, rects, texts, mousePosControl, mouseButtonControl);
        setText(-2, "CRAFTING");
        this.human = human;
        this.crafting = crafting;

        setText(CRAFTING_TEXTS_OFFSET, "Base");
        setText(CRAFTING_TEXTS_OFFSET + 1, "Base Reset");
        setText(CRAFTING_TEXTS_OFFSET + 3, "Primary");
        setText(CRAFTING_TEXTS_OFFSET + 4, "Primary Reset");
        setText(CRAFTING_TEXTS_OFFSET + 6, "Secondary");
        setText(CRAFTING_TEXTS_OFFSET + 7, "Secondary Reset");
        setText(CRAFTING_TEXTS_OFFSET + 9, "Enhance");
        setText(CRAFTING_TEXTS_OFFSET + 10, "Enhance Reset");

        setText(CRAFTING_TEXTS_BUTTONS_OFFSET, "Prev Item");
        setText(CRAFTING_TEXTS_BUTTONS_OFFSET + 1, "Next Item");
    }

    void setUiGlow(UiGlows uiGlows) {
        this.uiGlows = uiGlows;
    }

    @Override
    void setInvisible() {
        super.setInvisible();
        setText(CRAFTING_TEXTS_HINT, "");
    }

    @Override
    void updateTexts() {
        int highlighted = getHighlighted();
        if (highlighted < CRAFTING_TEXTS_OFFSET)
            highlighted = -1;
        else if (highlighted == CRAFTING_TEXTS_OFFSET + 2
                || highlighted == CRAFTING_TEXTS_OFFSET + 5
                || highlighted == CRAFTING_TEXTS_OFFSET + 8)
            highlighted = -1;
        else if (highlighted > CRAFTING_TEXTS_OFFSET + 10 && highlighted < CRAFTING_TEXTS_BUTTONS_OFFSET)
            highlighted = -1;

        setHighlight(highlighted);

        if (getClick() == MouseButton.PRIMARY && highlighted != -1) {
            Glows.Glow[] glowsSelected = uiGlows.getGlowsSelected();
            String hintText = null;
            boolean crafted = true;

            if (highlighted == CRAFTING_TEXTS_OFFSET)
                hintText = crafting.craftBase(glowsSelected);
            else if (highlighted == CRAFTING_TEXTS_OFFSET + 1)
                hintText = crafting.resetBase();

            else if (highlighted == CRAFTING_TEXTS_OFFSET + 3)
                hintText = crafting.craftPrimary(glowsSelected);
            else if (highlighted == CRAFTING_TEXTS_OFFSET + 4)
                hintText = crafting.resetPrimary();

            else if (highlighted == CRAFTING_TEXTS_OFFSET + 6)
                hintText = crafting.craftSecondary(glowsSelected);
            else if (highlighted == CRAFTING_TEXTS_OFFSET + 7)
                hintText = crafting.resetSecondary();

            else if (highlighted == CRAFTING_TEXTS_OFFSET + 9)
                hintText = crafting.craftEnhance();
            else if (highlighted == CRAFTING_TEXTS_OFFSET + 10)
                hintText = crafting.resetEnhance();

            else
                crafted = false;

            if (crafted) {
                setText(CRAFTING_TEXTS_HINT, hintText != null ? hintText : "");
                uiGlows.refreshSelectedGlows();
            }

            if (highlighted == CRAFTING_TEXTS_BUTTONS_OFFSET)
                crafting.selectInventoryGear(-1);
            else if (highlighted == CRAFTING_TEXTS_BUTTONS_OFFSET + 1)
                crafting.selectInventoryGear(1);
        }

        setText(0, crafting.getGearText());
        setText(1, crafting.getEnchantabilityText());
        for (int i = 0; i < Gear.GEAR_MAX_PROPERTIES; i++)
            setText(i + 2, crafting.getPropertyText(i));
    }
}