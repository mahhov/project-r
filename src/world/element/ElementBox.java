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
        color = new float[] {1, 1, 1, 1}; // todo staitc final
    }

    @Override
    public void connectWorld(World world, CubeInstancedFaces cubeInstancedFaces) {
        super.connectWorld(world, cubeInstancedFaces);
        world.addCube((int) x, (int) y, (int) z);
        world.addCube((int) x, (int) y, (int) z + 1);
        world.addCube((int) x, (int) y, (int) z + 2);
        world.addCube((int) x, (int) y, (int) z + 3);
    }

    @Override
    public boolean update(World world) {
        return false;
    }

    @Override
    public void draw() {
        cubeInstancedFaces.add(x, z + 1.5f, -y, 0, 0, 1, 4, 1, color, false);
    }
}
