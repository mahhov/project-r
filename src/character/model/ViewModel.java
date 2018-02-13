package character.model;

import control.KeyControl;
import modelviewer.Selector;
import util.LList;

public class ViewModel {
    private LList<Segment> segments;

    public ViewModel() {
        segments = new LList<>();
    }

    public void addSegment(Segment segment) {
        segments.addTail(segment);
    }

    public void update(Selector.Tool tool, KeyControl keyControl) {
    }

    public void draw() {
        for (Segment segment : segments)
            segment.draw();
    }
}