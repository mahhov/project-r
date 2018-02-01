package ui;

import character.Character;
import character.Human;
import character.Monster;
import shape.Rects;
import shape.Texts;
import world.WorldElement;

class UiHud {
    private static float BAR_WIDTH = .3f, BAR_HEIGHT = .03f, THIN_BAR_HEIGHT = .02f;
    static final float
            BAR_TROW1_TOP = 1 - UiDrawer.MEDIUM_MARGIN,
            BAR_TROW1_BOTTOM = BAR_TROW1_TOP - THIN_BAR_HEIGHT,
            BAR_TROW2_TOP = BAR_TROW1_BOTTOM - UiDrawer.SMALL_MARGIN,
            BAR_TROW2_BOTTOM = BAR_TROW2_TOP - THIN_BAR_HEIGHT,
            BAR_ROW2_BOTTOM = -1 + UiDrawer.MEDIUM_MARGIN,
            BAR_ROW2_TOP = BAR_ROW2_BOTTOM + BAR_HEIGHT,
            BAR_ROW1_BOTTOM = BAR_ROW2_TOP + UiDrawer.SMALL_MARGIN + UiBar.BORDER,
            BAR_ROW1_TOP = BAR_ROW1_BOTTOM + BAR_HEIGHT;
    static final float
            BAR_COL1_LEFT = -1 + UiDrawer.MEDIUM_MARGIN,
            BAR_COL1_RIGHT = BAR_COL1_LEFT + BAR_WIDTH,
            BAR_COL2_RIGHT = 1 - UiDrawer.MEDIUM_MARGIN,
            BAR_COL2_LEFT = BAR_COL2_RIGHT - BAR_WIDTH;

    private static final float
            BAR_ROW3_HEIGHT = THIN_BAR_HEIGHT,
            BAR_ROW3_TOP = BAR_ROW2_BOTTOM - UiDrawer.SMALL_MARGIN - UiBar.BORDER,
            BAR_ROW3_BOTTOM = BAR_ROW3_TOP - BAR_ROW3_HEIGHT,
            BAR_ROW3_TEXT_BOTTOM = -1 + UiDrawer.SMALL_MARGIN;

    private static final float BAR_ALPHA = 1;
    private static final float[] RESERVE_COLOR = new float[] {.2f, .6f, .6f, BAR_ALPHA}, STAMINA_COLOR = new float[] {1, .8f, .6f, BAR_ALPHA};
    private static final float[] SHIELD_COLOR = new float[] {.4f, .5f, .7f, BAR_ALPHA}, LIFE_COLOR = new float[] {.8f, .3f, .3f, BAR_ALPHA};
    private static final float[] EXPERIENCE_COLOR = new float[] {.9f, .6f, .1f, BAR_ALPHA};

    private UiBar pickShieldBar, pickLifeBar;
    private UiBar shieldBar, lifeBar;
    private UiBar reserveBar, staminaBar;
    private Texts.Text levelText;
    private UiBar experienceBar;

    private Human human;

    private boolean visible;

    UiHud(Rects rects, Texts texts, Human human) {
        pickShieldBar = new UiBar(BAR_COL1_RIGHT, BAR_TROW1_TOP, BAR_COL2_LEFT, BAR_TROW1_BOTTOM, SHIELD_COLOR, UiDrawer.BACK_COLOR, rects);
        pickLifeBar = new UiBar(BAR_COL1_RIGHT, BAR_TROW2_TOP, BAR_COL2_LEFT, BAR_TROW2_BOTTOM, LIFE_COLOR, UiDrawer.BACK_COLOR, rects);

        shieldBar = new UiBar(BAR_COL1_LEFT, BAR_ROW1_TOP, BAR_COL1_RIGHT, BAR_ROW1_BOTTOM, SHIELD_COLOR, UiDrawer.BACK_COLOR, rects);
        lifeBar = new UiBar(BAR_COL1_LEFT, BAR_ROW2_TOP, BAR_COL1_RIGHT, BAR_ROW2_BOTTOM, LIFE_COLOR, UiDrawer.BACK_COLOR, rects);
        reserveBar = new UiBar(BAR_COL2_LEFT, BAR_ROW1_TOP, BAR_COL2_RIGHT, BAR_ROW1_BOTTOM, RESERVE_COLOR, UiDrawer.BACK_COLOR, rects);
        staminaBar = new UiBar(BAR_COL2_LEFT, BAR_ROW2_TOP, BAR_COL2_RIGHT, BAR_ROW2_BOTTOM, STAMINA_COLOR, UiDrawer.BACK_COLOR, rects);

        levelText = texts.addText();
        levelText.setCoordinates(BAR_COL1_LEFT, BAR_ROW3_TOP, BAR_ROW3_TEXT_BOTTOM);
        experienceBar = new UiBar(BAR_COL1_LEFT, BAR_ROW3_TOP, BAR_COL2_RIGHT, BAR_ROW3_BOTTOM, EXPERIENCE_COLOR, UiDrawer.BACK_COLOR, rects);

        this.human = human;

        visible = true;
    }

    void hide() {
        if (visible) {
            pickShieldBar.hide();
            pickLifeBar.hide();
            shieldBar.hide();
            lifeBar.hide();
            reserveBar.hide();
            staminaBar.hide();
            levelText.disable();
            experienceBar.hide();
            visible = false;
        }
    }

    void show() {
        if (!visible) {
            shieldBar.show();
            lifeBar.show();
            reserveBar.show();
            staminaBar.show();
            levelText.enable();
            experienceBar.show();
            visible = true;
        }

        WorldElement pick = human.getPickElement();
        if (pick == null || pick.getId() != Character.WORLD_ELEMENT_ID) {
            pickShieldBar.hide();
            pickLifeBar.hide();
        } else {
            Monster monster = (Monster) pick;
            float shield = monster.getShieldPercent();
            if (shield != -1) {
                pickShieldBar.show();
                pickShieldBar.setPercentFill(shield);
            } else
                pickShieldBar.hide();

            pickLifeBar.show();
            pickLifeBar.setPercentFill(monster.getLifePercent());
        }
        shieldBar.setPercentFill(human.getShieldPercent());
        lifeBar.setPercentFill(human.getLifePercent());
        reserveBar.setPercentFill(human.getStaminaReservePercent());
        staminaBar.setPercentFill(human.getStaminaPercent());
        levelText.setText(human.getExperienceLevel() + "");
        experienceBar.setPercentFill(human.getExperiencePercent());
    }
}