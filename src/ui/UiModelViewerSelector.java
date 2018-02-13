package ui;

import control.MouseButton;
import control.MouseButtonControl;
import control.MousePosControl;
import modelviewer.Selector;
import shape.Rects;
import shape.Texts;

class UiModelViewerSelector extends UiInteractivePane {
    private static final int TOOL_SIZE = Selector.getToolCount(), SIZE = TOOL_SIZE + 3;
    private Selector selector;

    UiModelViewerSelector(float[] backColor, Rects rects, Texts texts, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl, Selector selector) {
        super(SIZE, 2, false, Location.LEFT, backColor, rects, texts, mousePosControl, mouseButtonControl);
        this.selector = selector;
        setText(-2, "SELECTOR");
        setText(TOOL_SIZE + 1, "Prev Segment");
        setText(TOOL_SIZE + 2, "Next Segment");
        setSelect(selector.getSelectedToolIndex());
    }

    @Override
    void updateTexts() {
        int highlighted = getHighlighted();
        if (highlighted == TOOL_SIZE)
            highlighted = -1;
        setHighlight(highlighted);

        if (getClick() == MouseButton.PRIMARY && highlighted != -1)
            if (highlighted < TOOL_SIZE) {
                selector.setTool(Selector.getTool(highlighted));
                setSelect(highlighted);
            } else if (highlighted == TOOL_SIZE + 1)
                selector.prevSegment();
            else
                selector.nextSegment();

        for (int i = 0; i < TOOL_SIZE; i++)
            setText(i, selector.getText(Selector.getTool(i)));
    }
}