package ui;

import control.*;
import modelviewer.Selector;

public class UiDrawerModelViewer extends UiDrawer {
    private UiModelViewerSelector modelViewerSelector;

    public UiDrawerModelViewer(Selector selector, KeyControl keyControl, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        super(keyControl, mousePosControl, mouseButtonControl);

        modelViewerSelector = new UiModelViewerSelector(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, selector);
    }

    @Override
    public void update() {
        if (keyControl.isKeyPressed(KeyButton.KEY_ENTER) || mouseButtonControl.isMousePressed(MouseButton.SECONDARY))
            modelViewerSelector.toggle();

        modelViewerSelector.update();

        rects.doneAdding();
        texts.doneAdding();
    }
}