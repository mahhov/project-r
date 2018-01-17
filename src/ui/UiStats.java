package ui;

import character.Stats;
import shape.Rects;
import shape.Texts;

class UiStats extends UiPane {
    private Stats stats;

    UiStats(float[] backColor, Rects rects, Texts texts, Stats stats) {
        super(stats.getStatTypeCount() + 2, false, Location.LEFT, backColor, rects, texts);
        setText(0, "STATS");

        this.stats = stats;
    }

    @Override
    void updateTexts() {
        for (int i = 2; i < size; i++)
            setText(i, stats.getText(Stats.getStatType(i - 2)));
    }
}