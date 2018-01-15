package ui;

import character.Experience;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

class UiCharacter extends UiTextListPane {
    private Experience experience;
    private MousePosControl mousePosControl;
    private MouseButtonControl mouseButtonControl;

    UiCharacter(float left, float top, float right, float bottom, float[] backColor, Rects rects, Texts texts, Experience experience, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        super(experience.getSkillCount() + 2, false, left, top, right, bottom, backColor, rects, texts);
        this.experience = experience;
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
        if (selected < 2)
            selected = -1;
        else if (mouseButtonControl.isMousePressed(MouseButtonControl.PRIMARY))
            experience.spendPoint(selected - 2);
        setHighlight(selected);

        setText(0, "Unspent Points: " + experience.getUnspentPoints());
        for (int i = 2; i < size; i++)
            setText(i, experience.getText(i - 2));
    }
}