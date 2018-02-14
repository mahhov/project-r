package ui;

import control.KeyControl;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

class UiDrawer {
    static final float SMALL_MARGIN = .02f, MEDIUM_MARGIN = .1f;
    static final float[] BACK_COLOR = new float[] {.3f, .3f, .3f, .5f};

    final Rects rects;
    final Texts texts;

    private Texts.Text fpsText;

    final KeyControl keyControl;
    final MousePosControl mousePosControl;
    final MouseButtonControl mouseButtonControl;

    UiDrawer(KeyControl keyControl, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        rects = new Rects(1000); // todo allow dynamic growing size
        texts = new Texts(1000);

        fpsText = texts.addText();
        fpsText.setCoordinates(-1, 1, .95f);

        this.keyControl = keyControl;
        this.mousePosControl = mousePosControl;
        this.mouseButtonControl = mouseButtonControl;
    }

    public void update() {
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

// todo determine how to split up ui package into sub packages