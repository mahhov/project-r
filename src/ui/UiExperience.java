package ui;

import character.Experience;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

class UiExperience extends UiTextListPane {
    private Experience experience;
    private MousePosControl mousePosControl;
    private MouseButtonControl mouseButtonControl;

    UiExperience(float[] backColor, Rects rects, Texts texts, Experience experience, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        super(experience.getSkillCount() + 2, false, Location.RIGHT, backColor, rects, texts);
        setText(0, "UNSPENT POINTS: " + experience.getUnspentPoints());

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
            experience.spendPoint(Experience.getSkill(selected - 2), 1);
        else if (mouseButtonControl.isMousePressed(MouseButtonControl.SECONDARY))
            experience.spendPoint(Experience.getSkill(selected - 2), 10);
        setHighlight(selected);

        for (int i = 2; i < size; i++)
            setText(i, experience.getText(Experience.getSkill(i - 2)));
    }
}