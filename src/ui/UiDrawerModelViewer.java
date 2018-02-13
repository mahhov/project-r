package ui;

import control.*;
import modelviewer.Selector;
import shape.Rects;
import shape.Texts;

public class UiDrawerModelViewer { // todo shared code with UiDrawer to move to common ancestor (named UiDrawer, rename UiDrawer to UiDrawerGame)
    static final float[] BACK_COLOR = new float[] {.3f, .3f, .3f, .5f};

    private Rects rects;
    private Texts texts;

    private UiModelViewerSelector modelViewerSelector;
    private Texts.Text fpsText;

    // controls
    private KeyControl keyControl;
    private MousePosControl mousePosControl;
    private MouseButtonControl mouseButtonControl;

    public UiDrawerModelViewer(Selector selector, KeyControl keyControl, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        rects = new Rects(1000); // todo allow dynamic growing size
        texts = new Texts(1000);

        modelViewerSelector = new UiModelViewerSelector(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, selector);

        fpsText = texts.addText();
        fpsText.setCoordinates(-1, 1, .95f);

        this.keyControl = keyControl;
        this.mousePosControl = mousePosControl;
        this.mouseButtonControl = mouseButtonControl;
    }

    public void update() {
        if (keyControl.isKeyPressed(KeyButton.KEY_ENTER) || mouseButtonControl.isMousePressed(MouseButton.SECONDARY))
            modelViewerSelector.toggle();

        modelViewerSelector.update();

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