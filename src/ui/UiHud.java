package ui;

import character.Human;
import shape.Rects;
import shape.Texts;

class UiHud {
    private static float BAR_WIDTH = .3f, BAR_HEIGHT = .03f;
    static final float
            BAR_ROW2_BOTTOM = -1 + UiDrawer.MEDIUM_MARGIN,
            BAR_ROW2_TOP = BAR_ROW2_BOTTOM + BAR_HEIGHT,
            BAR_ROW1_BOTTOM = BAR_ROW2_TOP + UiDrawer.SMALL_MARGIN,
            BAR_ROW1_TOP = BAR_ROW1_BOTTOM + BAR_HEIGHT;
    static final float
            BAR_COL1_LEFT = -1 + UiDrawer.MEDIUM_MARGIN,
            BAR_COL1_RIGHT = BAR_COL1_LEFT + BAR_WIDTH,
            BAR_COL2_RIGHT = 1 - UiDrawer.MEDIUM_MARGIN,
            BAR_COL2_LEFT = BAR_COL2_RIGHT - BAR_WIDTH;

    private static final float
            BAR_ROW3_HEIGHT = UiDrawer.SMALL_MARGIN,
            BAR_ROW3_TOP = BAR_ROW2_BOTTOM - UiDrawer.SMALL_MARGIN,
            BAR_ROW3_BOTTOM = BAR_ROW3_TOP - BAR_ROW3_HEIGHT,
            BAR_ROW3_TEXT_BOTTOM = -1 + UiDrawer.SMALL_MARGIN;

    private static final float BAR_ALPHA = 1;
    private static final float[] RESERVE_COLOR = new float[] {.2f, .6f, .6f, BAR_ALPHA}, STAMINA_COLOR = new float[] {1, .8f, .6f, BAR_ALPHA};
    private static final float[] SHIELD_COLOR = new float[] {.4f, .5f, .7f, BAR_ALPHA}, LIFE_COLOR = new float[] {.8f, .3f, .3f, BAR_ALPHA};
    private static final float[] EXPERIENCE_COLOR = new float[] {.9f, .6f, .1f, BAR_ALPHA};

    private UiBar reserveBar, staminaBar;
    private UiBar shieldBar, lifeBar;
    private Texts.Text levelText;
    private UiBar experienceBar;

    private Human human;

    private boolean visible;

    UiHud(Rects rects, Texts texts, Human human) {
        reserveBar = new UiBar(BAR_COL2_LEFT, BAR_ROW1_TOP, BAR_COL2_RIGHT, BAR_ROW1_BOTTOM, RESERVE_COLOR, UiDrawer.BACK_COLOR, rects);
        staminaBar = new UiBar(BAR_COL2_LEFT, BAR_ROW2_TOP, BAR_COL2_RIGHT, BAR_ROW2_BOTTOM, STAMINA_COLOR, UiDrawer.BACK_COLOR, rects);
        shieldBar = new UiBar(BAR_COL1_LEFT, BAR_ROW1_TOP, BAR_COL1_RIGHT, BAR_ROW1_BOTTOM, SHIELD_COLOR, UiDrawer.BACK_COLOR, rects);
        lifeBar = new UiBar(BAR_COL1_LEFT, BAR_ROW2_TOP, BAR_COL1_RIGHT, BAR_ROW2_BOTTOM, LIFE_COLOR, UiDrawer.BACK_COLOR, rects);

        levelText = texts.addText();
        levelText.setCoordinates(BAR_COL1_LEFT, BAR_ROW3_TOP, BAR_ROW3_TEXT_BOTTOM);
        experienceBar = new UiBar(BAR_COL1_LEFT, BAR_ROW3_TOP, BAR_COL2_RIGHT, BAR_ROW3_BOTTOM, EXPERIENCE_COLOR, UiDrawer.BACK_COLOR, rects);

        this.human = human;

        visible = true;
    }

    void hide() {
        if (visible) {
            reserveBar.hide();
            staminaBar.hide();
            shieldBar.hide();
            lifeBar.hide();
            levelText.disable();
            experienceBar.hide();
            visible = false;
        }
    }

    void show() {
        if (!visible) {
            reserveBar.show();
            staminaBar.show();
            shieldBar.show();
            lifeBar.show();
            levelText.enable();
            experienceBar.show();
            visible = true;
        }

        reserveBar.setPercentFill(human.getStaminaReservePercent());
        staminaBar.setPercentFill(human.getStaminaPercent());
        shieldBar.setPercentFill(human.getShieldPercent());
        lifeBar.setPercentFill(human.getLifePercent());
        levelText.setText(human.getExperienceLevel() + "");
        experienceBar.setPercentFill(human.getExperiencePercent());
    }
}