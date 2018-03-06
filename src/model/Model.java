package model;

import model.animation.AnimationSet;
import model.segment.Segment;
import model.segment.SegmentData;
import shape.CubeInstancedFaces;
import util.math.MathNumbers;

public class Model {
    private Segment[] segments;
    private int segmentCount;
    private AnimationSet animations;

    public Model(ModelData modelData, CubeInstancedFaces cubeInstancedFaces, float size) {
        float modelSize = MathNumbers.max(modelData.rightBoundary - modelData.leftBoundary, modelData.frontBoundary - modelData.backBoundary, modelData.topBoundary - modelData.bottomBoundary);
        float scale = size / modelSize;

        segments = new Segment[modelData.segmentCount];
        for (SegmentData segmentData : modelData.segmentData)
            addSegment(new Segment(segmentData, scale));

        for (int i = 0; i < modelData.segmentCount; i++)
            segments[i].init(modelData.parents[i] != -1 ? segments[modelData.parents[i]] : null, cubeInstancedFaces);

        animations = new AnimationSet(modelData.animationSetData);
    }

    private void addSegment(Segment segment) {
        segments[segmentCount++] = segment;
    }

    public void animate(AnimationSet.AnimationType animationType) {
        animations.animate(animationType);
    }
    
    public void animate() {
        animations.animate();
    }

    public void setTransform(float x, float y, float z, float theta) {
        segments[0].setTranslation(x, y, z);
        segments[0].setRotation(theta);
    }

    public void draw() {
        animations.apply(segments);
        for (Segment segment : segments)
            segment.draw();
    }
}