package world;

import camera.Follow;
import control.KeyControl;
import control.MousePosControl;
import geometry.CoordinateI3;
import shape.CubeInstancedFaces;
import util.MathAngles;
import util.MathNumbers;

public class Character implements WorldElement, Follow {
    private static final float MOVE_SEED = 1.5f, ROTATE_SPEED = .03f, ROTATE_SPEED_MOUSE = .008f;
    private static final float[] COLOR = new float[] {0, 1, 0};

    private float x, y, z;
    private float theta, thetaZ;

    private CubeInstancedFaces cubeInstancedFaces;

    public Character(float x, float y, float z, float theta, float thetaZ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.theta = theta;
        this.thetaZ = thetaZ;

        cubeInstancedFaces = new CubeInstancedFaces(COLOR);
    }

    @Override
    public void update(World world) {
        if (world.hasCube(new CoordinateI3((int) x, (int) y, (int) z)))
            z++;
        else if (!world.hasCube(new CoordinateI3((int) x, (int) y, (int) z - 1)))
            z--;
    }

    public void updateControls(KeyControl keyControl, MousePosControl mousePosControl) {
        // keyboard horizontal
        if (keyControl.isKeyDown(KeyControl.KEY_W)) {
            x -= MOVE_SEED * MathAngles.sin(theta);
            y += MOVE_SEED * MathAngles.cos(theta);
        }
        if (keyControl.isKeyDown(KeyControl.KEY_S)) {
            x += MOVE_SEED * MathAngles.sin(theta);
            y -= MOVE_SEED * MathAngles.cos(theta);
        }
        if (keyControl.isKeyDown(KeyControl.KEY_A)) {
            x -= MOVE_SEED * MathAngles.cos(theta);
            y -= MOVE_SEED * MathAngles.sin(theta);
        }
        if (keyControl.isKeyDown(KeyControl.KEY_D)) {
            x += MOVE_SEED * MathAngles.cos(theta);
            y += MOVE_SEED * MathAngles.sin(theta);
        }

        // keyboard vertical
        if (keyControl.isKeyDown(KeyControl.KEY_SHIFT))
            z -= MOVE_SEED;
        if (keyControl.isKeyDown(KeyControl.KEY_SPACE))
            z += MOVE_SEED;

        // mouse rotation
        theta -= ROTATE_SPEED_MOUSE * mousePosControl.getX();
        thetaZ = MathNumbers.maxMin(thetaZ - ROTATE_SPEED_MOUSE * mousePosControl.getY(), MathAngles.PI / 2, -MathAngles.PI / 2);
    }

    @Override
    public void draw() {
        cubeInstancedFaces.reset();
        cubeInstancedFaces.add(x, z + .5f, -y, theta, thetaZ);
        cubeInstancedFaces.doneAdding();
        cubeInstancedFaces.draw();
    }

    @Override
    public float getFollowX() {
        return x;
    }

    @Override
    public float getFollowY() {
        return z;
    }

    @Override
    public float getFollowZ() {
        return -y;
    }

    @Override
    public float getFollowTheta() {
        return theta;
    }

    @Override
    public float getFollowThetaZ() {
        return thetaZ;
    }
}