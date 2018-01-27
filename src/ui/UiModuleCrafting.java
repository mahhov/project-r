package ui;

import character.Glows;
import character.ModuleCrafting;
import character.gear.Gear;
import control.MouseButton;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

class UiModuleCrafting extends UiInteractivePane {
    private static final int CRAFTING_TEXTS_OFFSET = Gear.GEAR_MAX_PROPERTIES + 4, SELECTOR_TEXTS_OFFSET = CRAFTING_TEXTS_OFFSET + 3;
    private ModuleCrafting moduleCrafting;
    private UiGlows uiGlows;
    private GearWriter gearWriter;

    UiModuleCrafting(float[] backColor, Rects rects, Texts texts, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl, ModuleCrafting moduleCrafting) {
        super(SELECTOR_TEXTS_OFFSET + 2, 2, false, Location.RIGHT, backColor, rects, texts, mousePosControl, mouseButtonControl);
        this.moduleCrafting = moduleCrafting;

        gearWriter = new GearWriter(this, 0, "--Select Gear to Craft--");

        setText(-2, "MODULE CRAFTING");
        setText(CRAFTING_TEXTS_OFFSET, "Base");
        setText(CRAFTING_TEXTS_OFFSET + 1, "Base Reset");

        setText(SELECTOR_TEXTS_OFFSET, "Prev Item");
        setText(SELECTOR_TEXTS_OFFSET + 1, "Next Item");
    }

    void setUiGlow(UiGlows uiGlows) {
        this.uiGlows = uiGlows;
    }

    @Override
    void updateTexts() {
        int highlighted = getHighlighted();
        if (highlighted < CRAFTING_TEXTS_OFFSET || highlighted == CRAFTING_TEXTS_OFFSET + 2)
            highlighted = -1;

        setHighlight(highlighted);

        if (getClick() == MouseButton.PRIMARY && highlighted != -1) {
            Glows.Glow[] glowsSelected = uiGlows.getGlowsSelected();

            if (highlighted == CRAFTING_TEXTS_OFFSET) {
                moduleCrafting.craftBase(glowsSelected);
                uiGlows.refreshSelectedGlows();
            } else if (highlighted == CRAFTING_TEXTS_OFFSET + 1) {
                moduleCrafting.resetBase();
                uiGlows.refreshSelectedGlows();
            }

            if (highlighted == SELECTOR_TEXTS_OFFSET) {
                moduleCrafting.selectInventoryModule(-1);
                gearWriter.setGear(moduleCrafting.getModule());
            } else if (highlighted == SELECTOR_TEXTS_OFFSET + 1) {
                moduleCrafting.selectInventoryModule(1);
                gearWriter.setGear(moduleCrafting.getModule());
            } else
                gearWriter.refreshText();
        }
    }
}