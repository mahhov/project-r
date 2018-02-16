package model;

import model.segment.Segment;
import model.segment.SegmentData;
import shape.CubeInstancedFaces;
import world.WorldElement;

import java.io.IOException;
import java.io.ObjectInputStream;

public class Model {
    private Segment segments[];
    private int segmentCount;
    WorldElement worldElement;

    Model(WorldElement worldElement, int segmentCount) {
        segments = new Segment[segmentCount];
        this.worldElement = worldElement;
    }

    void addSegment(Segment segment) {
        segments[segmentCount++] = segment;
    }

    public void draw() {
        segments[0].setTranslation(worldElement.getX(), worldElement.getY(), worldElement.getZ());
        segments[0].setRotation(worldElement.getTheta());
        for (Segment segment : segments)
            segment.draw();
    }

    public static Model read(ObjectInputStream in, WorldElement worldElement, CubeInstancedFaces cubeInstancedFaces) {
        try {
            int segmentsCount = in.readInt();
            Model model = new Model(worldElement, segmentsCount);

            int[] parents = new int[segmentsCount];
            for (int i = 0; i < segmentsCount; i++)
                parents[i] = in.readInt();

            for (int i = 0; i < segmentsCount; i++)
                model.addSegment(new Segment((SegmentData) in.readObject()));

            for (int i = 0; i < segmentsCount; i++)
                model.segments[i].init(parents[i] != -1 ? model.segments[parents[i]] : null, cubeInstancedFaces);

            return model;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}