package character.model;

import character.Character;
import shape.CubeInstancedFaces;

public class Model {
    private Character character;
    private CubeInstancedFaces cubeInstancedFaces;
    private float size, color[];

    public Model(Character character, CubeInstancedFaces cubeInstancedFaces, float[] color) {
        this.character = character;
        this.cubeInstancedFaces = cubeInstancedFaces;
        this.color = color;
        size = character.getSize();
    }

    public void draw() {
        cubeInstancedFaces.add(character.getX(), character.getZ(), -character.getY(), character.getTheta(), 0, size, color);
    }
}