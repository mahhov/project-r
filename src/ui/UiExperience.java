package ui;

import character.Experience;
import control.MouseButton;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.Rects;
import shape.Texts;

class UiExperience extends UiInteractivePane {
    private Experience experience;

    UiExperience(float[] backColor, Rects rects, Texts texts, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl, Experience experience) {
        super(Experience.getSkillCount(), 2, false, Location.RIGHT, backColor, rects, texts, mousePosControl, mouseButtonControl);
        setText(-2, "UNSPENT POINTS: " + experience.getUnspentPoints());
        this.experience = experience;
    }

    @Override
    void updateTexts() {
        int highlighted = getHighlighted();
        MouseButton click = getClick();

        if (highlighted != -1)
            if (click == MouseButton.PRIMARY)
                experience.spendPoint(Experience.getSkill(highlighted), 1);
            else if (click == MouseButton.SECONDARY)
                experience.spendPoint(Experience.getSkill(highlighted), 10);
        setHighlight(highlighted);

        for (int i = 0; i < size; i++)
            setText(i, experience.getText(Experience.getSkill(i)));
    }
}