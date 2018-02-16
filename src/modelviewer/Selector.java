package modelviewer;

import control.KeyButton;
import control.KeyControl;

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

    void update(KeyControl keyControl) {
        if (keyControl.isKeyPressed(KeyButton.KEY_N))
            prevSegment();
        else if (keyControl.isKeyPressed(KeyButton.KEY_M))
            nextSegment();
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