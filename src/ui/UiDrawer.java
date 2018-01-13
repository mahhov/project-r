package ui;

import character.Human;
import control.KeyControl;
import shape.Rects;
import shape.Texts;

public class UiDrawer {
    private static float TINY_MARGIN = .02f, MEDIUM_MARGIN = .1f;

    private static float BAR_WIDTH = .3f, BAR_HEIGHT = .03f;
    private static final float
            BAR_ROW2_BOTTOM = -1 + MEDIUM_MARGIN,
            BAR_ROW2_TOP = BAR_ROW2_BOTTOM + BAR_HEIGHT,
            BAR_ROW1_BOTTOM = BAR_ROW2_TOP + TINY_MARGIN,
            BAR_ROW1_TOP = BAR_ROW1_BOTTOM + BAR_HEIGHT;
    private static final float
            BAR_COL1_LEFT = -1 + MEDIUM_MARGIN,
            BAR_COL1_RIGHT = BAR_COL1_LEFT + BAR_WIDTH,
            BAR_COL2_RIGHT = 1 - MEDIUM_MARGIN,
            BAR_COL2_LEFT = BAR_COL2_RIGHT - BAR_WIDTH;

    private static float CENTER_RECT_SIZE = .01f, CENTER_RECT_COLOR[] = new float[] {.1f, .5f, .3f};

    private static final float
            TEXT_BOX_HEIHT = .3f,
            TEXT_BOX_LEFT = BAR_COL1_RIGHT + TINY_MARGIN,
            TEXT_BOX_RIGHT = BAR_COL2_LEFT - TINY_MARGIN,
            TEXT_BOX_BOTTOM = -1 + MEDIUM_MARGIN,
            TEXT_BOX_TOP = TEXT_BOX_BOTTOM + TEXT_BOX_HEIHT;

    private static final float
            PANE_OFFSET = .13f,
            PANE_BOTTOM = TEXT_BOX_TOP + PANE_OFFSET,
            PANE_TOP = 1 - MEDIUM_MARGIN - PANE_OFFSET;

    private static final float[] BACK_COLOR = new float[] {.8f, .8f, .8f};
    private static final float[] RESERVE_COLOR = new float[] {.2f, .6f, .6f}, STAMINA_COLOR = new float[] {1, .8f, .6f};
    private static final float[] SHIELD_COLOR = new float[] {.4f, .5f, .7f}, LIFE_COLOR = new float[] {.8f, .3f, .3f};

    private Human human;
    private Rects rects;
    private Texts texts;

    private UiBar reserveBar, staminaBar;
    private UiBar shieldBar, lifeBar;

    private UiInventory inventory;
    private UiTextBox textBox;

    private Texts.Text fpsText;

    public UiDrawer(Human human) {
        this.human = human;
        rects = new Rects(11);
        texts = new Texts(1000);

        // center crosshair
        rects.addRect(CENTER_RECT_COLOR).setCoordinates(-CENTER_RECT_SIZE, CENTER_RECT_SIZE, CENTER_RECT_SIZE, -CENTER_RECT_SIZE);

        // life & stamina bars
        reserveBar = new UiBar(BAR_COL2_LEFT, BAR_ROW1_TOP, BAR_COL2_RIGHT, BAR_ROW1_BOTTOM, RESERVE_COLOR, BACK_COLOR, rects);
        staminaBar = new UiBar(BAR_COL2_LEFT, BAR_ROW2_TOP, BAR_COL2_RIGHT, BAR_ROW2_BOTTOM, STAMINA_COLOR, BACK_COLOR, rects);
        shieldBar = new UiBar(BAR_COL1_LEFT, BAR_ROW1_TOP, BAR_COL1_RIGHT, BAR_ROW1_BOTTOM, SHIELD_COLOR, BACK_COLOR, rects);
        lifeBar = new UiBar(BAR_COL1_LEFT, BAR_ROW2_TOP, BAR_COL1_RIGHT, BAR_ROW2_BOTTOM, LIFE_COLOR, BACK_COLOR, rects);

        // inventory
        inventory = new UiInventory(PANE_OFFSET, PANE_TOP, BAR_COL2_RIGHT, PANE_BOTTOM, BACK_COLOR, rects, texts, human.getInventory());

        // text box
        textBox = new UiTextBox(TEXT_BOX_LEFT, TEXT_BOX_TOP, TEXT_BOX_RIGHT, TEXT_BOX_BOTTOM, BACK_COLOR, rects, texts, human.getInventory());

        // text test
        fpsText = texts.addText();
        fpsText.setCoordinates(-1, 1, .95f);
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
        inventory.update();

        if (keyControl.isKeyPressed(KeyControl.KEY_Q))
            textBox.toggle();
        textBox.update();

        rects.doneAdding();
        texts.doneAdding();
    }

    public void updateFps(int fps) {
        fpsText.setText("fps " + fps);
    }

    public void draw() {
        rects.draw();
    }

    public void drawText() {
        texts.draw();
    }
}