package modelviewer;

public class Selector {
    public enum Tool {
        POSITION("Position"), SIZE("Size");

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

    int getSelectedSegmentDelta() {
        return segmentDelta;
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