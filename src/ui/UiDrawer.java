package ui;

import character.Human;
import control.KeyControl;
import shape.Rects;
import shape.Texts;

public class UiDrawer {
    private static float CENTER_RECT_SIZE = .01f, CENTER_RECT_COLOR[] = new float[] {.1f, .5f, .3f};
    private static final float BOTTOM_ROW1_TOP = -.82f, BOTTOM_ROW1_BOTTOM = -.85f, BOTTOM_ROW2_TOP = -.87f, BOTTOM_ROW2_BOTTOM = -.9f;
    private static final float LEFT_LEFT = -.9f, LEFT_RIGHT = -.6f, RIGHT_LEFT = .6f, RIGHT_RIGHT = .9f;
    private static final float PANE_X_OFFSET = .1f, PANE_BOTTOM = -.77f, PANE_TOP = .77f;

    private static final float[] BACK_COLOR = new float[] {.8f, .8f, .8f};
    private static final float[] RESERVE_COLOR = new float[] {.2f, .6f, .6f}, STAMINA_COLOR = new float[] {1, .8f, .6f};
    private static final float[] SHIELD_COLOR = new float[] {.4f, .5f, .7f}, LIFE_COLOR = new float[] {.8f, .3f, .3f};

    private Human human;
    private Rects rects;
    private Texts texts;

    private Bar reserveBar, staminaBar;
    private Bar shieldBar, lifeBar;

    private Inventory inventory;

    public UiDrawer(Human human) {
        this.human = human;
        rects = new Rects(10);
        texts = new Texts(100);

        // center crosshair
        rects.addRect(CENTER_RECT_COLOR).setCoordinates(-CENTER_RECT_SIZE, CENTER_RECT_SIZE, CENTER_RECT_SIZE, -CENTER_RECT_SIZE);

        // life & stamina bars
        reserveBar = new Bar(RIGHT_LEFT, BOTTOM_ROW1_TOP, RIGHT_RIGHT, BOTTOM_ROW1_BOTTOM, RESERVE_COLOR, BACK_COLOR, rects);
        staminaBar = new Bar(RIGHT_LEFT, BOTTOM_ROW2_TOP, RIGHT_RIGHT, BOTTOM_ROW2_BOTTOM, STAMINA_COLOR, BACK_COLOR, rects);
        shieldBar = new Bar(LEFT_LEFT, BOTTOM_ROW1_TOP, LEFT_RIGHT, BOTTOM_ROW1_BOTTOM, SHIELD_COLOR, BACK_COLOR, rects);
        lifeBar = new Bar(LEFT_LEFT, BOTTOM_ROW2_TOP, LEFT_RIGHT, BOTTOM_ROW2_BOTTOM, LIFE_COLOR, BACK_COLOR, rects);

        // inventory
        inventory = new Inventory(PANE_X_OFFSET, PANE_TOP, RIGHT_RIGHT, PANE_BOTTOM, LIFE_COLOR, BACK_COLOR, rects);

        // text test
        Texts.Text text = texts.addText();
        text.setCoordinates(-.5f, .5f, .5f, .25f);
        text.setText("mabc");
        texts.doneAdding();
    }

    public void update(KeyControl keyControl) {
        if (human.isFollowZoom()) {
            reserveBar.hide();
            staminaBar.hide();
            shieldBar.hide();
            lifeBar.hide();
        } else {
            reserveBar.show();
            staminaBar.show();
            shieldBar.show();
            lifeBar.show();
            reserveBar.setPercentFill(human.getStaminaReservePercent());
            staminaBar.setPercentFill(human.getStaminaPercent());
            shieldBar.setPercentFill(human.getShieldPercent());
            lifeBar.setPercentFill(human.getLifePercent());
        }

        if (keyControl.isKeyPressed(KeyControl.KEY_E))
            inventory.toggle();

        rects.doneAdding();
    }

    public void draw() {
        rects.draw();
    }

    public void drawText() {
        texts.draw();
    }
}