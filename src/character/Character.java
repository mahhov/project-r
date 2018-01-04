package character;

import shape.CubeInstancedFaces;
import util.IntersectionFinder;
import util.MathAngles;
import util.MathNumbers;
import world.World;
import world.WorldElement;

class Character implements WorldElement { // todo support human movement
    // mobility
    private static final float FRICTION = 0.8f, AIR_FRICTION = 0.97f, GRAVITY = .1f, JUMP_MULT = 1;
    private static final float JUMP_ACC = 1f, RUN_ACC = .07f, AIR_ACC = .02f;
    private boolean air;

    // life
    private Life life;

    // position
    private float x, y, z;
    private float vx, vy, vz;
    private float theta, thetaZ;
    private float[] norm, right;
    private IntersectionFinder intersectionFinder;

    private CubeInstancedFaces cubeInstancedFaces;

    Character(float x, float y, float z, float theta, float thetaZ, IntersectionFinder intersectionFinder, float life, float lifeRegen, float shield, float shieldRegen, float[] color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.theta = theta;
        this.thetaZ = thetaZ;
        norm = new float[2];
        right = new float[2];
        this.intersectionFinder = intersectionFinder;

        this.life = new Life(life, lifeRegen, shield, shieldRegen);

        cubeInstancedFaces = new CubeInstancedFaces(color);
    }

    @Override
    public void update(World world) {
    }

    public void move(MoveControl moveControl) {
        life.regen();

        doRotations(moveControl);
        computeAxis();
        doRunningMove(moveControl);

        if (moveControl.jump)
            doJump();

        vz -= GRAVITY;
        doFriction();

        applyVelocity();
    }

    private void doRotations(MoveControl moveControl) {
        theta = moveControl.theta;
        thetaZ = moveControl.thetaZ;
    }

    private void computeAxis() {
        // norm
        norm[0] = -MathAngles.sin(theta);
        norm[1] = MathAngles.cos(theta);

        // right
        right[0] = norm[1];
        right[1] = -norm[0];
    }

    private void doRunningMove(MoveControl moveControl) {
        float acc = air ? AIR_ACC : RUN_ACC;

        if (moveControl.forward) {
            vx += norm[0] * acc;
            vy += norm[1] * acc;
        }

        if (moveControl.backward) {
            vx -= norm[0] * acc;
            vy -= norm[1] * acc;
        }

        if (moveControl.left) {
            vx -= right[0] * acc;
            vy -= right[1] * acc;
        }

        if (moveControl.right) {
            vx += right[0] * acc;
            vy += right[1] * acc;
        }
    }

    private void doJump() {
        vx *= JUMP_MULT;
        vy *= JUMP_MULT;
        vz += JUMP_ACC;
    }

    private void doFriction() {
        float friction;
        if (!air)
            friction = FRICTION;
        else
            friction = AIR_FRICTION;

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
            air = false;
            vz = 0;
        } else
            air = true;

        if (intersection.collisionX)
            vx = 0;
        if (intersection.collisionY)
            vy = 0;
    }

    @Override
    public void draw() {
        cubeInstancedFaces.reset();
        cubeInstancedFaces.add(x, z, -y, theta, thetaZ);
        cubeInstancedFaces.doneAdding();
        cubeInstancedFaces.draw();
    }
}