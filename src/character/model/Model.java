package character.model;

import character.Character;
import shape.CubeInstancedFaces;

public class Model {
    private Character character;
    private Segment body, head;

    public Model(Character character, CubeInstancedFaces cubeInstancedFaces, float[] color) {
        this.character = character;
        float size = character.getSize();

        body = new Segment(null, size, color, cubeInstancedFaces);
        head = new Segment(body, size / 2, color, cubeInstancedFaces);
        head.setTranslation(0, 0, size * .75f);
    }

    public void draw() {
        body.setTranslation(character.getX(), character.getY(), character.getZ());
        body.setRotation(character.getTheta());
        body.draw();
        head.draw();
    }
}