package model;

import model.segment.SegmentData;
import util.Writer;

import java.io.IOException;
import java.io.Serializable;

public class ModelData implements Serializable {
    int segmentCount;
    int[] parents;
    SegmentData[] segmentData;

    ModelData(int segmentCount) {
        this.segmentCount = segmentCount;
        parents = new int[segmentCount];
        segmentData = new SegmentData[segmentCount];
    }

    public static ModelData readModelData(String fileName) {
        try {
            return (ModelData) Writer.getReadStream(fileName).readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}