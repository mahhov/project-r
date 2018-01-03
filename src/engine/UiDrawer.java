package engine;

import shape.Rects;
import world.Character;

class UiDrawer {
    private static final float[] STAMINA_COLOR = new float[] {1, .85f, .63f};

    private Character character;
    private Rects rects;
    private Rects.Rect staminaBar;

    UiDrawer(Character character) {
        this.character = character;
        rects = new Rects(2);
        staminaBar = rects.addRect(STAMINA_COLOR);
        Rects.Rect staminaBarBack = rects.addRect(new float[] {0, 0, 0});
        staminaBarBack.setCoordinates(.6f, -.87f, .9f, -.9f);
    }

    void draw() {
        staminaBar.setCoordinates(.6f, -.87f, .6f + .3f * character.stamina, -.9f);
        rects.doneAdding();
        rects.draw();
    }
}