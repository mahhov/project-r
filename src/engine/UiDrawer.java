package engine;

import character.Character;
import shape.Rects;

class UiDrawer {
    private static final float BOTTOM_ROW1_TOP = -.82f, BOTTOM_ROW1_BOTTOM = -.85f, BOTTOM_ROW2_TOP = -.87f, BOTTOM_ROW2_BOTTOM = -.9f;
    private static final float LEFT_LEFT = -.9f, LEFT_RIGHT = -.6f, RIGHT_LEFT = .6f, RIGHT_RIGHT = .9f;

    private static final float[] BACK_COLOR = new float[] {.2f, .2f, .2f};
    private static final float[] RESERVE_COLOR = new float[] {.2f, .5f, .6f}, STAMINA_COLOR = new float[] {1, .85f, .63f};
    private static final float[] SHIELD_COLOR = new float[] {}, LIFE_COLOR = new float[] {};

    private Character character;
    private Rects rects;
    private Bar reserveBar, staminaBar;
    private Bar shieldBar, lifeBar;

    UiDrawer(Character character) {
        this.character = character;
        rects = new Rects(9);

        reserveBar = new Bar(RIGHT_LEFT, BOTTOM_ROW1_TOP, RIGHT_RIGHT, BOTTOM_ROW1_BOTTOM, RESERVE_COLOR);
        staminaBar = new Bar(RIGHT_LEFT, BOTTOM_ROW2_TOP, RIGHT_RIGHT, BOTTOM_ROW2_BOTTOM, STAMINA_COLOR);

        shieldBar = new Bar(LEFT_LEFT, BOTTOM_ROW1_TOP, LEFT_RIGHT, BOTTOM_ROW1_BOTTOM, SHIELD_COLOR);
        lifeBar = new Bar(LEFT_LEFT, BOTTOM_ROW2_TOP, LEFT_RIGHT, BOTTOM_ROW2_BOTTOM, LIFE_COLOR);
    }

    void draw() {
        reserveBar.setCoordinates(character.getStaminaReservePercent());
        staminaBar.setCoordinates(character.getStaminaPercent());
        shieldBar.setCoordinates(character.getShieldPercent());
        lifeBar.setCoordinates(character.getLifePercent());
        rects.doneAdding();
        rects.draw();
    }

    private class Bar {
        private float left, top, width, bottom;
        private float[] color;
        private Rects.Rect rect;

        private Bar(float left, float top, float right, float bottom, float[] color) {
            this.left = left;
            this.top = top;
            width = right - left;
            this.bottom = bottom;
            this.color = color;

            rect = rects.addRect(color);
            rects.addRect(BACK_COLOR).setCoordinates(left, top, right, bottom);
        }

        private void setCoordinates(float percent) {
            rect.setCoordinates(left, top, left + width * percent, bottom);
        }
    }
}