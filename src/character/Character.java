package character;

import camera.Follow;
import control.KeyControl;
import control.MousePosControl;
import shape.CubeInstancedFaces;
import util.IntersectionFinder;
import util.MathAngles;
import util.MathNumbers;
import world.World;
import world.WorldElement;

public class Character implements WorldElement, Follow {
    private static final float ROTATE_SPEED_MOUSE = .008f;
    private static final float[] COLOR = new float[] {0, 1, 0};

    // mobility
    private static final float FRICTION = 0.8f, AIR_FRICTION = 0.97f, GRAVITY = .1f, JUMP_MULT = 1;
    private static final float JUMP_ACC = 1f, JET_ACC = .11f, RUN_ACC = .07f, AIR_ACC = .02f;
    private static final float BOOST_ACC = .07f, GLIDE_ACC = .05f, GLIDE_DESCENT_ACC = .02f;
    private static final int STATE_GROUND = 0, STATE_AIR = 1; // todo change to boolean air
    private int state;
    private boolean jetting;

    // ability
    private static final int JUMP_MAX = 2;
    private ChargesAbility jumpAbility;
    private static final int BOOST_COOLDOWN = 60, BOOST_DURATION = 20;
    private CooldownAbility boostAbility;
    private RechargeAbility glideAbility, jetAbility; // todo intigrate

    // position
    private float x, y, z;
    private float vx, vy, vz;
    private float theta, thetaZ;
    private float[] norm, right;
    public float stamina;
    private IntersectionFinder intersectionFinder;

    private CubeInstancedFaces cubeInstancedFaces;

    public Character(float x, float y, float z, float theta, float thetaZ, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.theta = theta;
        this.thetaZ = thetaZ;
        norm = new float[2];
        right = new float[2];
        intersectionFinder = new IntersectionFinder(world);

        jumpAbility = new ChargesAbility(JUMP_MAX);
        boostAbility = new CooldownAbility(BOOST_COOLDOWN, BOOST_DURATION);

        cubeInstancedFaces = new CubeInstancedFaces(COLOR);
    }

    @Override
    public void update(World world) {
    }

    public void updateControls(KeyControl keyControl, MousePosControl mousePosControl) {
        boolean shiftPress = keyControl.isKeyPressed(KeyControl.KEY_SHIFT);

        if (stamina < 1) {
            stamina += .005f;
            if (stamina > 1)
                stamina = 1;
        }

        doRotations(mousePosControl);
        computeAxis();
        doRunningMove(keyControl);

        if (keyControl.isKeyPressed(KeyControl.KEY_SPACE))
            jump();
        if (keyControl.isKeyDown(KeyControl.KEY_SPACE))
            doUpwardMove();
        else
            jetting = false;

        doBoost(shiftPress);

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

        // right
        right[0] = norm[1];
        right[1] = -norm[0];
    }

    private void doRunningMove(KeyControl keyControl) {
        float acc;
        if (boostAbility.active())
            acc = BOOST_ACC;
        else if (state == STATE_GROUND)
            acc = RUN_ACC;
        else if (keyControl.isKeyDown(KeyControl.KEY_SHIFT) && stamina > .01f) {
            stamina -= .01f;
            acc = GLIDE_ACC;
            vz -= GLIDE_DESCENT_ACC;
        } else
            acc = AIR_ACC;

        if (keyControl.isKeyDown(KeyControl.KEY_W)) {
            vx += norm[0] * acc;
            vy += norm[1] * acc;
        }

        if (keyControl.isKeyDown(KeyControl.KEY_S)) {
            vx -= norm[0] * acc;
            vy -= norm[1] * acc;
        }

        if (keyControl.isKeyDown(KeyControl.KEY_A)) {
            vx -= right[0] * acc;
            vy -= right[1] * acc;
        }

        if (keyControl.isKeyDown(KeyControl.KEY_D)) {
            vx += right[0] * acc;
            vy += right[1] * acc;
        }
    }

    private void jump() {
        if (!jumpAbility.ready())
            return;
        jumpAbility.useCharge();
        vx *= JUMP_MULT;
        vy *= JUMP_MULT;
        vz += JUMP_ACC;
    }

    private void doUpwardMove() {
        vz += JET_ACC;
        jetting = true;
    }

    private void doBoost(boolean tryActivate) {
        boostAbility.update();
        if (tryActivate && boostAbility.ready())
            boostAbility.activate();
    }

    private void doFriction() {
        float friction;
        if (state == STATE_GROUND && !boostAbility.active())
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
            state = STATE_GROUND;
            jumpAbility.resetCharges();
            vz = 0;
        } else
            state = STATE_AIR;

        if (intersection.collisionX)
            vx = 0;
        if (intersection.collisionY)
            vy = 0;
    }

    public float getBoostAbility() {
        return boostAbility.percent();
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