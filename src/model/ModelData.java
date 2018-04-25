package model;

import model.animation.AnimationSetData;
import model.segment.SegmentData;
import util.Writer;

import java.io.IOException;
import java.io.Serializable;

public class ModelData implements Serializable {
    public enum ModelType {
        BUG("bug.model"),
        MECH("mech.model"),
        GOAT("goat.model"),
        FOUR_LEG("fourLeg.model"),
        BIRD("bird.model"),
        PILLAR("pillar.model"),
        RHINO("rhino.model");

        public final ModelData modelData;

        ModelType(String file) {
            modelData = ModelData.readModelData(file);
        }
    }

    int segmentCount;
    int[] parents;
    SegmentData[] segmentData;
    AnimationSetData animationSetData;
    float leftBoundary, rightBoundary, backBoundary, frontBoundary, bottomBoundary, topBoundary;

    ModelData(int segmentCount) {
        this.segmentCount = segmentCount;
        parents = new int[segmentCount];
        segmentData = new SegmentData[segmentCount];
    }

    private static ModelData readModelData(String fileName) {
        try {
            return (ModelData) Writer.getReadStream(fileName).readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
