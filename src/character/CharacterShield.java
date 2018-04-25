package character;

import geometry.CoordinateI3;
import shape.CubeInstancedFaces;
import util.LList;
import world.World;
import world.WorldElement;

public class CharacterShield implements WorldElement {
    private static final int WORLD_ELEMENT_ID = 3;

    private float x, y, z;
    private float theta;
    private float size;
    private float color[];

    private CubeInstancedFaces cubeInstancedFaces;

    private CoordinateI3 worldCoordinate;
    private LList<WorldElement>.Node worldElementNode;

    CharacterShield(float size) {
        this.size = size;
        color = new float[] {1, 1, 1, .3f}; // todo static final
    }

    public void connectWorld(World world, CubeInstancedFaces cubeInstancedFaces) {
        this.cubeInstancedFaces = cubeInstancedFaces;
    }

    public void remove(World world) {
        world.removeDynamicElement(worldCoordinate, worldElementNode);
    }

    public void move(World world, float x, float y, float z, float theta) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.theta = theta;
    }

    @Override
    public boolean update(World world) {
        System.out.println("up");
        return false;
    }

    private void moveInWorld(World world) {
        CoordinateI3 newWorldCoordinate = new CoordinateI3((int) x, (int) y, (int) z);
        if (worldElementNode != null)
            worldElementNode = world.moveDynamicElement(worldCoordinate, newWorldCoordinate, worldElementNode);
        else
            worldElementNode = world.addDynamicElement(newWorldCoordinate, this);
        worldCoordinate = newWorldCoordinate;
    }

    @Override
    public void takeDamage(float amount) {
    }

    @Override
    public void draw() {
        // cubeInstancedFaces.add(x, z, -y, 0, 0, size, size, size, color, false);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }

    @Override
    public float getTheta() {
        return theta;
    }

    @Override
    public float getSize() {
        return size;
    }

    @Override
    public int getId() {
        return WORLD_ELEMENT_ID;
    }
}
