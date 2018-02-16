package model;

import model.segment.Segment;
import shape.CubeInstancedFaces;
import world.WorldElement;

public class GoatModel extends Model {
    private Segment body, head;

    public GoatModel(WorldElement worldElement, CubeInstancedFaces cubeInstancedFaces, float[] color) {
        super(worldElement, 2);

        float size = worldElement.getSize();

        body = new Segment(color);
        body.init(null, cubeInstancedFaces);
        body.setScale(size);

        head = new Segment(color);
        head.init(body, cubeInstancedFaces);
        head.setScale(size / 2);
        head.setTranslation(0, 0, size * .75f);

        addSegment(body);
        addSegment(head);
    }

    public void draw() {
        body.setTranslation(worldElement.getX(), worldElement.getY(), worldElement.getZ());
        body.setRotation(worldElement.getTheta());
        super.draw();
    }
}