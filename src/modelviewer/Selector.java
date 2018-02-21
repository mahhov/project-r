package modelviewer;

import control.KeyButton;
import control.KeyControl;
import control.MouseScrollControl;
import util.math.MathNumbers;

public class Selector {
    public enum Tool {
        POSITION("Position"), SCALE("Scale");

        public final String name;
        final int value;

        Tool(String name) {
            this.name = name;
            this.value = ordinal();
        }
    }

    private static final Tool[] TOOL_VALUES = Tool.values();

    private int segmentDelta;
    private Tool tool;

    Selector() {
        this.tool = Tool.POSITION;
    }

    void update(KeyControl keyControl, MouseScrollControl mouseScrollControl) {
        int scroll = mouseScrollControl.getScroll();
        int scrollX = mouseScrollControl.getScrollX();

        if (keyControl.isKeyPressed(KeyButton.KEY_N) || scroll < 0)
            prevSegment();
        else if (keyControl.isKeyPressed(KeyButton.KEY_M) || scroll > 0)
            nextSegment();

        if (scrollX < 0)
            setTool(getTool(MathNumbers.max(tool.value - 1, 0)));
        else if (scrollX > 0)
            setTool(getTool(MathNumbers.min(tool.value + 1, getToolCount() - 1)));
    }

    int getSelectedSegmentDelta() {
        int returnSegmentDelta = segmentDelta;
        segmentDelta = 0;
        return returnSegmentDelta;
    }

    Tool getSelectedTool() {
        return tool;
    }

    public int getSelectedToolIndex() {
        return tool.value;
    }

    public void prevSegment() {
        segmentDelta = -1;
    }

    public void nextSegment() {
        segmentDelta = 1;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public String getText(Tool tool) {
        return tool.name;
    }

    public static int getToolCount() {
        return TOOL_VALUES.length;
    }

    public static Tool getTool(int i) {
        return TOOL_VALUES[i];
    }
}