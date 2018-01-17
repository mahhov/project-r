package ui;

import character.Experience;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

class UiExperience extends UiInteractivePane {
    private Experience experience;

    UiExperience(float[] backColor, Rects rects, Texts texts, Experience experience, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        super(Experience.getSkillCount() + 2, false, Location.RIGHT, backColor, rects, texts, mousePosControl, mouseButtonControl);
        setText(0, "UNSPENT POINTS: " + experience.getUnspentPoints());
        this.experience = experience;
    }

    @Override
    void updateTexts() {
        int selected = getSelected();
        int click = getClick();

        if (selected < 2)
            selected = -1;
        else if (click == MouseButtonControl.PRIMARY)
            experience.spendPoint(Experience.getSkill(selected - 2), 1);
        else if (click == MouseButtonControl.SECONDARY)
            experience.spendPoint(Experience.getSkill(selected - 2), 10);
        setHighlight(selected);

        for (int i = 2; i < size; i++)
            setText(i, experience.getText(Experience.getSkill(i - 2)));
    }
}