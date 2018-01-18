package ui;

import control.MouseButtonControl;
import control.MousePosControl;
import map.Map;
import shape.Rects;
import shape.Texts;

class UiMap extends UiInteractivePane {
    private static final int SIZE = 16;
    private Map map;

    UiMap(float[] backColor, Rects rects, Texts texts, Map map, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        super(SIZE, 2, false, Location.CENTER, backColor, rects, texts, mousePosControl, mouseButtonControl);
        setText(-2, "MAP");
        this.map = map;
        String[] mapTexts = map.getTexts();
        for (int i = 0; i < size; i++)
            setText(i, mapTexts[i]);
    }

    @Override
    void updateTexts() {
        int selected = getSelected();
        if (selected != -1 && getClick() == MouseButtonControl.PRIMARY)
            map.load(selected);
        setHighlightAndRefreshText(selected);
    }
}