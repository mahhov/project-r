package world;

import camera.Follow;
import control.KeyControl;
import control.MousePosControl;
import geometry.Coordinate;
import shape.CubeInstancedFaces;
import util.IntersectionFinder;
import util.MathAngles;
import util.MathNumbers;

public class Character implements WorldElement, Follow {
    private static final float MOVE_SEED = .5f, ROTATE_SPEED_MOUSE = .008f;
    private static final float[] COLOR = new float[] {0, 1, 0};

    private float x, y, z;
    private float theta, thetaZ;
    private IntersectionFinder intersectionFinder;

    private CubeInstancedFaces cubeInstancedFaces;

    public Character(float x, float y, float z, float theta, float thetaZ, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.theta = theta;
        this.thetaZ = thetaZ;
        intersectionFinder = new IntersectionFinder(world);

        cubeInstancedFaces = new CubeInstancedFaces(COLOR);
    }

    @Override
    public void update(World world) {
    }

    public void updateControls(KeyControl keyControl, MousePosControl mousePosControl) {
        // keyboard horizontal
        float dx = 0, dy = 0, dz = -.3f;

        if (keyControl.isKeyDown(KeyControl.KEY_W)) {
            dx -= MOVE_SEED * MathAngles.sin(theta);
            dy += MOVE_SEED * MathAngles.cos(theta);
        }
        if (keyControl.isKeyDown(KeyControl.KEY_S)) {
            dx += MOVE_SEED * MathAngles.sin(theta);
            dy -= MOVE_SEED * MathAngles.cos(theta);
        }
        if (keyControl.isKeyDown(KeyControl.KEY_A)) {
            dx -= MOVE_SEED * MathAngles.cos(theta);
            dy -= MOVE_SEED * MathAngles.sin(theta);
        }
        if (keyControl.isKeyDown(KeyControl.KEY_D)) {
            dx += MOVE_SEED * MathAngles.cos(theta);
            dy += MOVE_SEED * MathAngles.sin(theta);
        }

        // keyboard vertical
        if (keyControl.isKeyDown(KeyControl.KEY_SHIFT))
            dz -= MOVE_SEED;
        if (keyControl.isKeyDown(KeyControl.KEY_SPACE))
            dz += MOVE_SEED;

        if (dx != 0 || dy != 0 || dz != 0) {
            float move = MathNumbers.magnitude(dx, dy, dz);
            Coordinate coordinate = intersectionFinder.find(new float[] {x, y, z}, new float[] {dx, dy, dz}, move, 1);
            x = coordinate.getX();
            y = coordinate.getY();
            z = coordinate.getZ();
        }

        // mouse rotation
        theta -= ROTATE_SPEED_MOUSE * mousePosControl.getX();
        thetaZ = MathNumbers.maxMin(thetaZ - ROTATE_SPEED_MOUSE * mousePosControl.getY(), MathAngles.PI / 2, -MathAngles.PI / 2);
    }

    @Override
    public void draw() {
        cubeInstancedFaces.reset();
        cubeInstancedFaces.add(x, z, -y, theta, thetaZ);
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