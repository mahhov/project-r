package ui;

import control.MouseButton;
import control.MouseButtonControl;
import control.MousePosControl;
import modelviewer.Selector;
import shape.Rects;
import shape.Texts;

class UiModelViewerSelector extends UiInteractivePane {
    private static final int SIZE = Selector.getToolCount();
    private Selector selector;

    UiModelViewerSelector(float[] backColor, Rects rects, Texts texts, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl, Selector selector) {
        super(SIZE, 2, false, Location.LEFT, backColor, rects, texts, mousePosControl, mouseButtonControl);
        this.selector = selector;
        setText(-2, "SELECTOR");
        setSelect(selector.getSelectedToolIndex());
    }

    @Override
    void updateTexts() {
        int highlighted = getHighlighted();
        setHighlight(highlighted);

        if (getClick() == MouseButton.PRIMARY && highlighted != -1) {
            selector.set(Selector.getTool(highlighted));
            setSelect(highlighted);
        }

        for (int i = 0; i < SIZE; i++)
            setText(i, selector.getText(Selector.getTool(i)));

    }
}