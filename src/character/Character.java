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
    private static final float STAMINA = 20, STAMINA_REGEN = .1f, STAMINA_RESERVE = 150, STAMINA_RESERVE_REGEN = .05f;
    private Stamina stamina;
    private static final int BOOST_COOLDOWN = 60, BOOST_DURATION = 20;
    private AbilityTimer boostTimer;

    // position
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
        norm = new float[2];
        right = new float[2];
        intersectionFinder = new IntersectionFinder(world);

        stamina = new Stamina(STAMINA, STAMINA_REGEN, STAMINA_RESERVE, STAMINA_RESERVE_REGEN);
        boostTimer = new AbilityTimer(BOOST_COOLDOWN, BOOST_DURATION);

        cubeInstancedFaces = new CubeInstancedFaces(COLOR);
    }

    @Override
    public void update(World world) {
    }

    public void updateControls(KeyControl keyControl, MousePosControl mousePosControl) {
        boolean shiftPress = keyControl.isKeyPressed(KeyControl.KEY_SHIFT);

        stamina.regen();

        doRotations(mousePosControl);
        computeAxis();
        doRunningMove(keyControl);

        if (keyControl.isKeyPressed(KeyControl.KEY_SPACE))
            jump();
        if (keyControl.isKeyDown(KeyControl.KEY_SPACE))
            doUpwardMove();
        else
            jetting = false;

        boostTimer.update();
        if (shiftPress)
            doBoost();

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
        if (boostTimer.active())
            acc = BOOST_ACC;
        else if (state == STATE_GROUND)
            acc = RUN_ACC;
        else if (keyControl.isKeyDown(KeyControl.KEY_SHIFT) && stamina.available(Stamina.GLIDE)) {
            stamina.deplete(Stamina.GLIDE);
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
        float staminaRequired = state == STATE_AIR ? Stamina.AIR_JUMP : Stamina.JUMP;
        if (!stamina.available(staminaRequired))
            return;
        stamina.deplete(staminaRequired);
        vx *= JUMP_MULT;
        vy *= JUMP_MULT;
        vz += JUMP_ACC;
    }

    private void doUpwardMove() {
        if (!stamina.available(Stamina.JET))
            return;
        stamina.deplete(Stamina.JET);
        vz += JET_ACC;
        jetting = true;
    }

    private void doBoost() {
        if (boostTimer.ready() && stamina.available(Stamina.BOOST)) {
            stamina.deplete(Stamina.BOOST);
            boostTimer.activate();
        }
    }

    private void doFriction() {
        float friction;
        if (state == STATE_GROUND && !boostTimer.active())
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
            vz = 0;
        } else
            state = STATE_AIR;

        if (intersection.collisionX)
            vx = 0;
        if (intersection.collisionY)
            vy = 0;
    }

    public float getStaminaPercent() {
        return stamina.percent();
    }

    public float getStaminaReservePercent() {
        return stamina.percentReserve();
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