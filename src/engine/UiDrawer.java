package engine;

import shape.Rect;
import world.Character;

class UiDrawer {
    private static final float[] STAMINA_COLOR = new float[] {1, .85f, .63f};

    private Character character;
    private Rect staminaBar;

    UiDrawer(Character character) {
        this.character = character;
        staminaBar = new Rect(.6f, -.87f, .9f, -.9f, STAMINA_COLOR);
    }

    void draw() {
        staminaBar.draw();
    }
}