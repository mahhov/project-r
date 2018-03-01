package model;

import model.animation.AnimationData;
import model.segment.Segment;
import model.segment.SegmentEditable;
import util.LList;
import util.math.MathNumbers;

class ModelCreator {
    private LList<SegmentEditable> segments;
    private AnimationData animationData;
    private float leftBoundary, rightBoundary, backBoundary, frontBoundary, bottomBoundary, topBoundary;

    ModelCreator() {
        segments = new LList<>();
    }

    void addSegment(SegmentEditable segment) {
        segments.addTail(segment);

        leftBoundary = MathNumbers.min(leftBoundary, segment.left());
        rightBoundary = MathNumbers.max(rightBoundary, segment.right());
        backBoundary = MathNumbers.min(backBoundary, segment.back());
        frontBoundary = MathNumbers.max(frontBoundary, segment.front());
        bottomBoundary = MathNumbers.min(bottomBoundary, segment.bottom());
        topBoundary = MathNumbers.max(topBoundary, segment.top());
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

        modelData.leftBoundary = leftBoundary;
        modelData.rightBoundary = rightBoundary;
        modelData.backBoundary = backBoundary;
        modelData.frontBoundary = frontBoundary;
        modelData.bottomBoundary = bottomBoundary;
        modelData.topBoundary = topBoundary;

        return modelData;
    }
}