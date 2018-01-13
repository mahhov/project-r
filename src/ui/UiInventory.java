package ui;

import character.Inventory;
import shape.Rects;
import shape.Texts;
import util.LList;

class UiInventory {
    private static final int ITEM_SIZE = 8, LOG_SIZE = 8;
    private static final float MARGIN = 0.01f;
    private Rects.Rect backRect;
    private Texts.Text itemTexts[], logTexts[];

    private boolean visible;
    private Inventory inventory;

    UiInventory(float left, float top, float right, float bottom, float[] backColor, Rects rects, Texts texts, Inventory inventory) {
        backRect = rects.addRect(backColor);
        backRect.setCoordinates(left, top, right, bottom);
        backRect.disable();

        float height = (top - bottom - MARGIN) / (ITEM_SIZE + LOG_SIZE) - MARGIN;

        itemTexts = new Texts.Text[ITEM_SIZE];
        for (int i = 0; i < ITEM_SIZE; i++) {
            itemTexts[i] = texts.addText();
            float topOffset = top - MARGIN - (MARGIN + height) * i;
            itemTexts[i].setCoordinates(left + MARGIN, topOffset, topOffset - height);
        }

        logTexts = new Texts.Text[LOG_SIZE];
        for (int i = 0; i < LOG_SIZE; i++) {
            logTexts[i] = texts.addText();
            float topOffset = top - MARGIN - (MARGIN + height) * (i + ITEM_SIZE);
            logTexts[i].setCoordinates(left + MARGIN, topOffset, topOffset - height);
        }

        this.inventory = inventory;
    }

    void toggle() {
        visible = !visible;
        if (visible) {
            backRect.enable();
            for (Texts.Text text : itemTexts)
                text.enable();
        } else {
            backRect.disable();
            for (Texts.Text text : itemTexts)
                text.disable();
        }
    }

    void update() {
        if (visible) {
            for (int i = 0; i < ITEM_SIZE; i++)
                itemTexts[i].setText(inventory.getItem(i) == null ? "" : inventory.getItem(i).print());

            LList<String> log = inventory.getLog();
            int removeCount = log.size() - LOG_SIZE;
            for (LList<String>.Node logNode : log.nodeIterator()) {
                if (removeCount-- > 0) {
                    log.remove(logNode);
                } else
                    logTexts[-removeCount - 1].setText(logNode.getValue());
            }
        }
    }
}