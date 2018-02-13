package character.model;

import character.Character;
import character.model.segment.Segment;

public abstract class Model {
    Character character;
    private Segment segments[];
    private int segmentCount;

    Model(Character character, int segmentCount) {
        this.character = character;
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