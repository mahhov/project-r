package ui;

import shape.Rects;
import shape.Texts;
import util.MathNumbers;

abstract class UiPane {
    private static final float
            BOTTOM_PANE_HEIHT = .3f,
            BOTTOM_PANE_LEFT = UiHud.BAR_COL1_RIGHT + UiDrawer.SMALL_MARGIN,
            BOTTOM_PANE_RIGHT = UiHud.BAR_COL2_LEFT - UiDrawer.SMALL_MARGIN,
            BOTTOM_PANE_BOTTOM = UiHud.BAR_ROW2_BOTTOM,
            BOTTOM_PANE_TOP = BOTTOM_PANE_BOTTOM + BOTTOM_PANE_HEIHT;

    private static final float
            PANE_OFFSET = .13f,
            PANE_BOTTOM = BOTTOM_PANE_TOP + PANE_OFFSET,
            PANE_TOP = 1 - UiDrawer.MEDIUM_MARGIN - PANE_OFFSET;

    enum Location {
        LEFT(UiHud.BAR_COL1_LEFT, PANE_TOP, -PANE_OFFSET, PANE_BOTTOM, 30),
        RIGHT(PANE_OFFSET, PANE_TOP, UiHud.BAR_COL2_RIGHT, PANE_BOTTOM, 30),
        CENTER(UiHud.BAR_COL1_LEFT, PANE_TOP, UiHud.BAR_COL2_RIGHT, PANE_BOTTOM, 30),
        BOTTOM(BOTTOM_PANE_LEFT, BOTTOM_PANE_TOP, BOTTOM_PANE_RIGHT, BOTTOM_PANE_BOTTOM, 8);

        private final float left, top, right, bottom;
        private final int rows;

        Location(float left, float top, float right, float bottom, int rows) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.rows = rows;
        }
    }

    private static final float[] HIGHLIGHT_COLOR = new float[] {0, 1, 0, 1}, SELECT_COLOR = new float[] {0, .5f, 1, 1};
    private static final float MARGIN = 0.03f, INNER_MARGIN = .001f;
    final int size;
    private int offset;
    private int highlighted, selectedLast;
    private boolean[] selected;
    private float itemLeft, itemTop, itemRight, itemBottom, itemOffsetY, itemHeight, itemHeightRatio;
    private Rects.Rect backRect;
    private Texts.Text texts[];
    private boolean visible;

    UiPane(int size, int offset, boolean visible, Location location, float[] backColor, Rects rects, Texts texts) {
        this.size = size;
        this.offset = offset;
        int sizeWithOffset = size + offset;

        highlighted = selectedLast = -1;
        selected = new boolean[size];

        backRect = rects.addRect(backColor);
        backRect.setCoordinates(location.left, location.top, location.right, location.bottom);

        int rows = MathNumbers.max(sizeWithOffset, location.rows);
        itemLeft = location.left + MARGIN;
        itemTop = location.top - MARGIN;
        itemRight = location.right - MARGIN;
        itemBottom = location.bottom + MARGIN;
        itemOffsetY = (itemTop - itemBottom + INNER_MARGIN) / rows;
        itemHeight = itemOffsetY - INNER_MARGIN;
        itemHeightRatio = itemHeight / itemOffsetY;
        this.texts = new Texts.Text[sizeWithOffset];
        for (int i = 0; i < sizeWithOffset; i++) {
            this.texts[i] = texts.addText();
            float topOffset = itemTop - itemOffsetY * i;
            this.texts[i].setCoordinates(itemLeft, topOffset, topOffset - itemHeight);
        }

        if (visible)
            setVisible();
        else
            setInvisible();
    }

    void toggle() {
        if (visible)
            setInvisible();
        else
            setVisible();
    }

    void setVisible() {
        visible = true;
        backRect.enable();
        for (Texts.Text text : texts)
            text.enable();
    }

    void setInvisible() {
        visible = false;
        backRect.disable();
        for (Texts.Text text : texts)
            text.disable();
    }

    boolean isVisible() {
        return visible;
    }

    void setText(int i, String text) {
        texts[i + offset].setText(text);
    }

    void setHighlight(int i) {
        int prevHighlighted = highlighted;
        highlighted = i;
        refreshColor(prevHighlighted);
        refreshColor(highlighted);
    }

    void setSelect(int i) {
        int prevSelected = selectedLast;
        selectedLast = i;
        refreshColor(prevSelected);
        refreshColor(selectedLast);
    }

    void toggleSelect(int i) {
        selected[i] = !selected[i];
        refreshColor(i);
    }

    int getSelectedLast() {
        return selectedLast;
    }

    boolean isSelected(int i) {
        return selected[i];
    }

    boolean[] getSelectedToggles() {
        return selected;
    }

    int[] getSelectedList() {
        int[] selectedList = new int[size + 1];
        int count = 0;
        for (int i = 0; i < size; i++)
            if (selected[i])
                selectedList[++count] = i;
        selectedList[0] = count;
        return selectedList;
    }

    private void refreshColor(int i) {
        if (i != -1)
            if (i == selectedLast || selected[i])
                texts[i + offset].setColor(SELECT_COLOR);
            else if (i == highlighted)
                texts[i + offset].setColor(HIGHLIGHT_COLOR);
            else
                texts[i + offset].setColor(null);
    }

    void update() {
        if (visible)
            updateTexts(); // todo don't call updateTexts every loop
    }

    int getIntersecting(float x, float y) {
        if (x < itemLeft || x > itemRight || y > itemTop || y < itemBottom)
            return -1;
        float shiftedY = (itemTop - y) / itemOffsetY;
        int intShiftedY = (int) shiftedY;
        if (intShiftedY < offset)
            return -1;
        intShiftedY -= offset;
        if (intShiftedY >= size)
            return -1;
        if (shiftedY - intShiftedY - offset > itemHeightRatio)
            return -1;
        return intShiftedY;
    }

    abstract void updateTexts();
}