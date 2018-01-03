package world;

import camera.Follow;
import control.KeyControl;
import control.MousePosControl;
import shape.CubeInstancedFaces;
import util.IntersectionFinder;
import util.MathAngles;
import util.MathNumbers;

public class Character implements WorldElement, Follow {
    private static final float ROTATE_SPEED_MOUSE = .008f;
    private static final float[] COLOR = new float[] {0, 1, 0};

    private static final float FRICTION = 0.9f, AIR_FRICTION = 0.97f, CLIMB_FRICTION = .99f, GRAVITY = .1f, COLLISION_DAMPER = .1f;
    private static final float JUMP_ACC = 1.5f, RUN_ACC = .1f, AIR_RUN_ACC = .04f, JET_ACC = .09f, CLIMB_ACC = .055f, JUMP_MULT = 1.5f;

    private static final int JUMP_MAX = 3;
    private int jumpRemain;

    private static final int STATE_GROUND = 0, STATE_CLIMB = 1, STATE_AIR = 2;
    private int state;
    private boolean jetting;

    private float x, y, z;
    private float vx, vy, vz;
    private float theta, thetaZ;
    private float[] norm, right;
    private IntersectionFinder intersectionFinder;

    private CubeInstancedFaces cubeInstancedFaces;

    public Character(float x, float y, float z, float theta, float thetaZ, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.theta = theta;
        this.thetaZ = thetaZ;
        norm = new float[3];
        right = new float[3];
        intersectionFinder = new IntersectionFinder(world);

        cubeInstancedFaces = new CubeInstancedFaces(COLOR);
    }

    @Override
    public void update(World world) {
    }

    public void updateControls(KeyControl keyControl, MousePosControl mousePosControl) {
        doRotations(mousePosControl);
        computeAxis();
        runningMove(keyControl);

        if (keyControl.isKeyPressed(KeyControl.KEY_SPACE))
            jump(); // todo prevent key repeat
        if (keyControl.isKeyDown(KeyControl.KEY_SPACE))
            upwardMove();
        else
            jetting = false;

        vz -= GRAVITY;
        doFriction();

        applyVelocity();
    }


    private void doRotations(MousePosControl mousePosControl) {
        theta -= ROTATE_SPEED_MOUSE * mousePosControl.getX();
        thetaZ = MathNumbers.maxMin(thetaZ - ROTATE_SPEED_MOUSE * mousePosControl.getY(), MathAngles.PI / 2, -MathAngles.PI / 2);
    }

    private void computeAxis() {
        // norm
        norm[0] = -MathAngles.sin(theta);
        norm[1] = MathAngles.cos(theta);
        norm[2] = 0; // todo remove z if always 0

        // right
        right[0] = norm[1];
        right[1] = -norm[0];
        right[2] = 0;
    }

    private void runningMove(KeyControl keyControl) {
        float acc = state == STATE_GROUND ? RUN_ACC : AIR_RUN_ACC;

        if (keyControl.isKeyDown(KeyControl.KEY_W)) {
            vx += norm[0] * acc;
            vy += norm[1] * acc;
            vz += norm[2] * acc;
        }

        if (keyControl.isKeyDown(KeyControl.KEY_S)) {
            vx -= norm[0] * acc;
            vy -= norm[1] * acc;
            vz -= norm[2] * acc;
        }

        if (keyControl.isKeyDown(KeyControl.KEY_A)) {
            vx -= right[0] * acc;
            vy -= right[1] * acc;
            vz -= right[2] * acc;
        }

        if (keyControl.isKeyDown(KeyControl.KEY_D)) {
            vx += right[0] * acc;
            vy += right[1] * acc;
            vz += right[2] * acc;
        }
    }

    private void jump() {
        if (jumpRemain == 0)
            return;
        System.out.println("JUMP!");
        jumpRemain--;
        vx *= JUMP_MULT;
        vy *= JUMP_MULT;
        vz += JUMP_ACC;
    }

    private void upwardMove() {
        if (state == STATE_CLIMB) {
            vz += CLIMB_ACC;
            jetting = false;
        } else {
            vz += JET_ACC;
            jetting = true;
        }
    }

    private void doFriction() {
        float friction;
        if (state == STATE_AIR)
            friction = AIR_FRICTION;
        else if (state == STATE_CLIMB)
            friction = CLIMB_FRICTION;
        else
            friction = FRICTION;

        vx *= friction;
        vy *= friction;
        vz *= friction;
    }

    private void applyVelocity() {
        IntersectionFinder.Intersection intersection = intersectionFinder.find(new float[] {x, y, z}, new float[] {vx, vy, vz}, MathNumbers.magnitude(vx, vy, vz), 1);
        x = intersection.coordinate.getX();
        y = intersection.coordinate.getY();
        z = intersection.coordinate.getZ();

        if (intersection.grounded) {
            state = STATE_GROUND;
            jumpRemain = JUMP_MAX;
            vz = 0;
        } else
            state = intersection.collisionX || intersection.collisionY ? STATE_AIR : STATE_AIR; // todo set state climb if space and collide

        //        boolean collide[] = terrain.getIntersectionCollide();
        //        if (collide[2]) {
        //            vz *= COLLISION_DAMPER;
        //            state = STATE_GROUND;
        //            jumpRemain = JUMP_MAX;
        //        } else if (collide[0]) {
        //            vx *= COLLISION_DAMPER;
        //            state = STATE_CLIMB;
        //        } else if (collide[1]) {
        //            vy *= COLLISION_DAMPER;
        //            state = STATE_CLIMB;
        //        }
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