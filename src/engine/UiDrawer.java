package engine;

import character.Character;
import shape.Rects;

class UiDrawer {
    private static final float[] BACK_COLOR = new float[] {.2f, .2f, .2f};
    private static final float[] RESERVE_COLOR = new float[] {.2f, .5f, .6f};
    private static final float[] STAMINA_COLOR = new float[] {1, .85f, .63f};

    private Character character;
    private Rects rects;
    private Rects.Rect reserveBar, staminaBar;

    UiDrawer(Character character) {
        this.character = character;
        rects = new Rects(4);

        reserveBar = rects.addRect(RESERVE_COLOR);
        staminaBar = rects.addRect(STAMINA_COLOR);
        
        Rects.Rect reserveBack = rects.addRect(BACK_COLOR);
        Rects.Rect staminaBack = rects.addRect(BACK_COLOR);

        reserveBack.setCoordinates(.6f, -.82f, .9f, -.85f);
        staminaBack.setCoordinates(.6f, -.87f, .9f, -.9f);
    }

    void draw() {
        reserveBar.setCoordinates(.6f, -.82f, .6f + .3f * character.getStaminaReservePercent(), -.85f);
        staminaBar.setCoordinates(.6f, -.87f, .6f + .3f * character.getStaminaPercent(), -.9f);
        rects.doneAdding();
        rects.draw();
    }
}