package ui;

import control.KeyButton;
import control.KeyControl;
import control.MouseButtonControl;
import control.MousePosControl;
import modelviewer.Selector;

public class UiDrawerModelViewer extends UiDrawer {
    private UiModelViewerSelector modelViewerSelector;

    public UiDrawerModelViewer(Selector selector, KeyControl keyControl, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        super(keyControl, mousePosControl, mouseButtonControl);

        modelViewerSelector = new UiModelViewerSelector(BACK_COLOR, rects, texts, mousePosControl, mouseButtonControl, selector);
    }

    @Override
    public void update() {
        if (keyControl.isKeyPressed(KeyButton.KEY_ENTER))
            modelViewerSelector.toggle();

        modelViewerSelector.update();

        rects.doneAdding();
        texts.doneAdding();
    }
}