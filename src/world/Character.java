package world;

import camera.Follow;
import engine.Controller;
import geometry.CoordinateI3;
import shape.CubeInstancedFaces;
import util.MathAngles;
import util.MathNumbers;

public class Character implements WorldElement, Follow {
    private static final float MOVE_SEED = 3f, ROTATE_SPEED = .03f;
    private static final float FOLLOW_DISTANCE = 20;
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

    public void updateControls(Controller controller) {
        if (controller.isKeyDown(Controller.KEY_W)) {
            x -= MOVE_SEED * MathAngles.sin(theta);
            y += MOVE_SEED * MathAngles.cos(theta);
        }
        if (controller.isKeyDown(Controller.KEY_S)) {
            x += MOVE_SEED * MathAngles.sin(theta);
            y -= MOVE_SEED * MathAngles.cos(theta);
        }
        if (controller.isKeyDown(Controller.KEY_A)) {
            x -= MOVE_SEED * MathAngles.cos(theta);
            y -= MOVE_SEED * MathAngles.sin(theta);
        }
        if (controller.isKeyDown(Controller.KEY_D)) {
            x += MOVE_SEED * MathAngles.cos(theta);
            y += MOVE_SEED * MathAngles.sin(theta);
        }

        if (controller.isKeyDown(Controller.KEY_SHIFT))
            z -= MOVE_SEED;
        if (controller.isKeyDown(Controller.KEY_SPACE))
            z += MOVE_SEED;

        if (controller.isKeyDown(Controller.KEY_Q))
            theta += ROTATE_SPEED;
        if (controller.isKeyDown(Controller.KEY_E))
            theta -= ROTATE_SPEED;
        if (controller.isKeyDown(Controller.KEY_R))
            thetaZ = MathNumbers.min(thetaZ + ROTATE_SPEED, MathAngles.PI / 2);
        if (controller.isKeyDown(Controller.KEY_F))
            thetaZ = MathNumbers.max(thetaZ - ROTATE_SPEED, -MathAngles.PI / 2);
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
        return x + MathAngles.sin(theta) * FOLLOW_DISTANCE;
    }

    @Override
    public float getFollowY() {
        return z + FOLLOW_DISTANCE / 2;
    }

    @Override
    public float getFollowZ() {
        return -y + MathAngles.cos(theta) * FOLLOW_DISTANCE;
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