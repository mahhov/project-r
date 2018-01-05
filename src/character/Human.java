package character;

import camera.Follow;
import control.KeyControl;
import control.MouseButtonControl;
import control.MousePosControl;
import shape.CubeInstancedFaces;
import util.IntersectionFinder;
import util.MathAngles;
import util.MathNumbers;
import world.World;
import world.WorldElement;

public class Human implements WorldElement, Follow {
    private static final float ROTATE_SPEED_MOUSE = .008f;
    private static final float[] COLOR = new float[] {0, 1, 0};

    // mobility
    private static final float FRICTION = 0.8f, AIR_FRICTION = 0.97f, GRAVITY = .1f, JUMP_MULT = 1;
    private static final float JUMP_ACC = 1f, JET_ACC = .11f, RUN_ACC = .07f, AIR_ACC = .02f;
    private static final float BOOST_ACC = .07f, GLIDE_ACC = .05f, GLIDE_DESCENT_ACC = .02f;
    private boolean air;
    private boolean jetting;

    // ability
    private static final float STAMINA = 20, STAMINA_REGEN = .1f, STAMINA_RESERVE = 150, STAMINA_RESERVE_REGEN = .05f;
    private Stamina stamina;
    private static final int BOOST_COOLDOWN = 60, BOOST_DURATION = 20, THROW_COOLDOWN = 15;
    private AbilityTimer boostTimer, throwTimer;

    // life
    private static final float LIFE = 100, LIFE_REGEN = .1f, SHIELD = 100, SHIELD_REGEN = 1;
    private Life life;

    // position
    private float x, y, z;
    private float vx, vy, vz;
    private float theta, thetaZ;
    private float[] norm, right;
    private IntersectionFinder intersectionFinder;

    private CubeInstancedFaces cubeInstancedFaces;

    public Human(float x, float y, float z, float theta, float thetaZ, IntersectionFinder intersectionFinder) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.theta = theta;
        this.thetaZ = thetaZ;
        norm = new float[2];
        right = new float[2];
        this.intersectionFinder = intersectionFinder;

        stamina = new Stamina(STAMINA, STAMINA_REGEN, STAMINA_RESERVE, STAMINA_RESERVE_REGEN);
        boostTimer = new AbilityTimer(BOOST_COOLDOWN, BOOST_DURATION);
        throwTimer = new AbilityTimer(THROW_COOLDOWN, 1);

        life = new Life(LIFE, LIFE_REGEN, SHIELD, SHIELD_REGEN);

        cubeInstancedFaces = new CubeInstancedFaces(COLOR);
    }

    @Override
    public void update(World world) {
    }

    public void updateControls(KeyControl keyControl, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        boolean shiftPress = keyControl.isKeyPressed(KeyControl.KEY_SHIFT);

        stamina.regen();
        life.regen();

        doRotations(mousePosControl);
        computeAxis();
        doRunningMove(keyControl);

        if (keyControl.isKeyPressed(KeyControl.KEY_SPACE))
            doJump();
        if (keyControl.isKeyDown(KeyControl.KEY_SPACE))
            doJet();
        else
            jetting = false;

        doBoost(shiftPress);

        vz -= GRAVITY;
        doFriction();

        applyVelocity();

        doThrow(mouseButtonControl.isMouseDown(MouseButtonControl.PRIMARY));
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

    private void doRunningMove(KeyControl keyControl) { // todo make it so diagonall movement is no faster than vertical/horizontal
        float acc;
        if (boostTimer.active())
            acc = BOOST_ACC;
        else if (!air)
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

    private void doJump() {
        float staminaRequired = air ? Stamina.AIR_JUMP : Stamina.JUMP;
        if (!stamina.available(staminaRequired))
            return;
        stamina.deplete(staminaRequired);
        vx *= JUMP_MULT;
        vy *= JUMP_MULT;
        vz += JUMP_ACC;
    }

    private void doJet() {
        if (!stamina.available(Stamina.JET))
            return;
        stamina.deplete(Stamina.JET);
        vz += JET_ACC;
        jetting = true;
    }

    private void doBoost(boolean shiftPress) {
        boostTimer.update();
        if (shiftPress && boostTimer.ready() && stamina.available(Stamina.BOOST) && !air) {
            stamina.deplete(Stamina.BOOST);
            boostTimer.activate();
        }
    }

    private void doFriction() {
        float friction;
        if (!air && !boostTimer.active())
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

    private void doThrow(boolean primaryDown) {
        throwTimer.update();
        if (primaryDown && throwTimer.ready() && stamina.available(Stamina.THROW)) {
            stamina.deplete(Stamina.THROW);
            throwTimer.activate();
            System.out.println("Throw");
        }
    }

    void takeDamage(int amount) {
        life.deplete(amount);
    }

    public float getStaminaPercent() {
        return stamina.percent();
    }

    public float getStaminaReservePercent() {
        return stamina.percentReserve();
    }

    public float getLifePercent() {
        return life.percentLife();
    }

    public float getShieldPercent() {
        return life.percentShield();
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

    float getX() {
        return x;
    }

    float getY() {
        return y;
    }

    float getZ() {
        return z;
    }
}