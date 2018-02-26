package model;

import model.animation.AnimationData;
import model.segment.Segment;
import util.LList;

class ModelCreator {
    private LList<Segment> segments;
    private AnimationData animationData;

    ModelCreator() {
        segments = new LList<>();
    }

    void addSegment(Segment segment) {
        segments.addTail(segment);
    }

    void setAnimationData(AnimationData animationData) {
        this.animationData = animationData;
    }

    ModelData getModelData() {
        ModelData modelData = new ModelData(segments.size());

        int segmentIndex = 0;
        for (Segment segment : segments) {
            modelData.parents[segmentIndex] = -1;
            int match = 0;
            for (Segment segmentInner : segments) {
                if (segment.getParent() == segmentInner) {
                    modelData.parents[segmentIndex] = match;
                    break;
                }
                match++;
            }
            modelData.segmentData[segmentIndex] = segment.getSegmentData();
            segmentIndex++;
        }

        modelData.animationData = animationData;

        return modelData;
    }
}