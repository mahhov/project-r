package engine;

import shape.Rect;
import world.Character;

class UiDrawer {
    private Character character;
    private Rect rect;

    UiDrawer(Character character) {
        this.character = character;
        rect = new Rect(.1f, .9f, .9f, .1f, new float[] {1, 0, 0});
    }

    void draw() {
//        rect.draw();
    }
}