package engine;

import character.Character;
import shape.Rects;
import util.MathNumbers;

class UiDrawer {
    private static final float[] BACK_COLOR = new float[] {.2f, .2f, .2f};
    private static final float[] BOOST_COLOR = new float[] {.2f, .5f, .6f};
    //    private static final float[] STAMINA_COLOR = new float[] {1, .85f, .63f};

    private Character character;
    private Rects rects;
    private Rects.Rect boostAbilityBar;

    UiDrawer(Character character) {
        this.character = character;
        rects = new Rects(2);

        boostAbilityBar = rects.addRect(BOOST_COLOR);
        Rects.Rect boostAbilityBack = rects.addRect(BACK_COLOR);
        boostAbilityBack.setCoordinates(.6f, -.87f, .9f, -.9f);
    }

    void draw() {
        boostAbilityBar.setCoordinates(.6f, -.87f, .9f - .3f * MathNumbers.max(character.getBoostAbility(), 0), -.9f);
        rects.doneAdding();
        rects.draw();
    }
}