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
        LEFT(UiHud.BAR_COL1_LEFT, PANE_TOP, -PANE_OFFSET, PANE_BOTTOM, 24),
        RIGHT(PANE_OFFSET, PANE_TOP, UiHud.BAR_COL2_RIGHT, PANE_BOTTOM, 24),
        CENTER(UiHud.BAR_COL1_LEFT, PANE_TOP, UiHud.BAR_COL2_RIGHT, PANE_BOTTOM, 24),
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

    private static final float[] HIGHLIGHT_COLOR = new float[] {0, 1, 0, 1};
    private static final float MARGIN = 0.01f;
    final int size;
    private float itemLeft, itemTop, itemRight, itemBottom, itemOffsetY, itemHeight, itemHeightRatio;
    private Rects.Rect backRect;
    private Texts.Text texts[];
    private int highlighted;

    private boolean visible;

    UiPane(int size, boolean visible, Location location, float[] backColor, Rects rects, Texts texts) { // todo support title
        this.size = size;

        backRect = rects.addRect(backColor);
        backRect.setCoordinates(location.left, location.top, location.right, location.bottom);

        int rows = MathNumbers.max(size, location.rows);
        itemLeft = location.left + MARGIN;
        itemTop = location.top - MARGIN;
        itemRight = location.right - MARGIN;
        itemBottom = location.bottom + MARGIN;
        itemOffsetY = (itemTop - location.bottom) / rows;
        itemHeight = itemOffsetY - MARGIN;
        itemHeightRatio = itemHeight / itemOffsetY;
        this.texts = new Texts.Text[rows];
        for (int i = 0; i < rows; i++) {
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
        texts[i].setText(text);
    }

    void setHighlight(int i) {
        if (highlighted != -1)
            texts[highlighted].setColor(null);
        highlighted = i;
        if (highlighted != -1)
            texts[highlighted].setColor(HIGHLIGHT_COLOR);
    }

    void setHighlightAndRefreshText(int i) {
        if (highlighted != -1) {
            texts[highlighted].setColor(null);
            texts[highlighted].refreshText();
        }
        highlighted = i;
        if (highlighted != -1) {
            texts[highlighted].setColor(HIGHLIGHT_COLOR);
            texts[highlighted].refreshText();
        }
    }

    void update() {
        if (visible)
            updateTexts();
    }

    int getIntersecting(float x, float y) {
        if (x < itemLeft || x > itemRight || y > itemTop || y < itemBottom)
            return -1;
        float shiftedY = (itemTop - y) / itemOffsetY;
        int intShiftedY = (int) shiftedY;
        if (intShiftedY >= size)
            return -1;
        if (shiftedY - intShiftedY > itemHeightRatio)
            return -1;
        return intShiftedY;
    }

    abstract void updateTexts();
}