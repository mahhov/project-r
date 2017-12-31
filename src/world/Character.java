package world;

import camera.Follow;
import engine.Controller;
import util.MathAngles;
import util.MathNumbers;

public class Character implements WorldElement, Follow {
    private static final float MOVE_SEED = 3f, ROTATE_SPEED = .03f;

    private float x, y, z;
    private float theta, thetaZ;

    public Character(float x, float y, float z, float theta, float thetaZ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.theta = theta;
        this.thetaZ = thetaZ;
    }

    @Override
    public void update() {

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