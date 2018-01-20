package ui;

import character.Stats;
import shape.Rects;
import shape.Texts;

class UiStats extends UiPane {
    private Stats stats;

    UiStats(float[] backColor, Rects rects, Texts texts, Stats stats) { // todo try giving different back colors to each pane
        super(Stats.getStatTypeCount(), 2, false, Location.LEFT, backColor, rects, texts);
        setText(-2, "STATS");
        this.stats = stats;
    }

    @Override
    void updateTexts() {
        for (int i = 0; i < size; i++)
            setText(i, stats.getText(Stats.getStatType(i)));
    }
}