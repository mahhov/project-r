package ui;

import control.MouseButtonControl;
import control.MousePosControl;
import map.Map;
import shape.Rects;
import shape.Texts;

class UiMap extends UiTextListPane {
    private static final int SIZE = 16;
    private Map map;
    private MousePosControl mousePosControl;
    private MouseButtonControl mouseButtonControl;

    UiMap(float left, float top, float right, float bottom, float[] backColor, Rects rects, Texts texts, Map map, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        super(SIZE, false, left, top, right, bottom, backColor, rects, texts);
        this.map = map;
        this.mousePosControl = mousePosControl;
        this.mouseButtonControl = mouseButtonControl;
    }

    @Override
    void setVisible() {
        super.setVisible();
        if (mousePosControl != null)
            mousePosControl.unlock();
    }

    @Override
    void setInvisible() {
        super.setInvisible();
        if (mousePosControl != null)
            mousePosControl.lock();
    }

    @Override
    void updateTexts() {
        int selected = getIntersecting(mousePosControl.getAbsX(), mousePosControl.getAbsY());
        int i = 0;
        for (String text : map.getTexts()) {
            setText(i, i == selected ? "SELECTED" : text);
            if (++i == size)
                break;
        }
    }
}