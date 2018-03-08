package world.element;

import shape.CubeInstancedFaces;
import world.World;

public class ElementBox extends Element {
    private float x, y, z;
    private float color[];

    public ElementBox(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        color = new float[] {1, 1, 1, 1};
    }

    @Override
    public void connectWorld(World world, CubeInstancedFaces cubeInstancedFaces) {
        super.connectWorld(world, cubeInstancedFaces);
        world.addCube((int) x, (int) y, (int) z);
    }

    @Override
    public boolean update(World world) {
        return false;
    }

    @Override
    public void draw() {
        cubeInstancedFaces.add(x, z, -y, 0, 0, 1, color);
    }
}