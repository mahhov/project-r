package model;

import model.segment.Segment;

public abstract class Model {
    private Segment segments[];
    private int segmentCount;

    Model(int segmentCount) {
        segments = new Segment[segmentCount];
    }

    void addSegment(Segment segment) {
        segments[segmentCount++] = segment;
    }

    public void draw() {
        for (Segment segment : segments)
            segment.draw();
    }
}