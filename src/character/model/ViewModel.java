package character.model;

import util.LList;

public class ViewModel {
    private LList<Segment> segments;

    public ViewModel() {
        segments = new LList<>();
    }

    public void addSegment(Segment segment) {
        segments.addTail(segment);
    }

    public void draw() {
        for (Segment segment : segments)
            segment.draw();
    }
}