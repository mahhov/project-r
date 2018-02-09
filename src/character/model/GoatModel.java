package character.model;

import character.Character;
import shape.CubeInstancedFaces;

public class GoatModel extends Model {
    private Segment body, head;

    public GoatModel(Character character, CubeInstancedFaces cubeInstancedFaces, float[] color) {
        super(character, 2);

        float size = character.getSize();
        body = new Segment(null, color, cubeInstancedFaces);
        body.setScale(size);
        head = new Segment(body, color, cubeInstancedFaces);
        head.setScale(size / 2);
        head.setTranslation(0, 0, size * .75f);

        addSegment(body);
        addSegment(head);
    }

    public void draw() {
        body.setTranslation(character.getX(), character.getY(), character.getZ());
        body.setRotation(character.getTheta());
        super.draw();
    }
}