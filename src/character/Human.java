package character;

import camera.Follow;
import control.KeyControl;
import control.MouseButtonControl;
import control.MousePosControl;
import item.Item;
import shape.CubeInstancedFaces;
import util.MathAngles;
import util.MathNumbers;
import util.intersection.Intersection;
import util.intersection.IntersectionMover;
import util.intersection.IntersectionPicker;
import world.World;
import world.WorldElement;
import world.projectile.Projectile;

public class Human implements WorldElement, Follow {
    private static final float ROTATE_SPEED_MOUSE = .008f;
    private static final float[] COLOR = new float[] {0, 1, 0};

    // mobility
    private static final float FRICTION = 0.8f, AIR_FRICTION = 0.97f, GRAVITY = .1f, JUMP_MULT = 1;
    private boolean air;

    private static final float RUN_ACC = .07f;
    private static final float JUMP_ACC = 1f;
    private static final float AIR_ACC = .02f;
    private static final float JET_ACC = .11f;
    private static final float BOOST_ACC = .07f;
    private static final float GLIDE_ACC = .05f;
    private static final float GLIDE_DESCENT_ACC = .02f;
    private static final float STAMINA = 20;
    private static final float STAMINA_REGEN = .1f;
    private static final float STAMINA_RESERVE = 150;
    private static final float STAMINA_RESERVE_REGEN = .05f;
    private static final float LIFE = 100;
    private static final float LIFE_REGEN = .1f;
    private static final float SHIELD = 100;
    private static final float SHIELD_REGEN = 1;
    private static final float SHIELD_REGEN_DELAY = 75;

    private Stats stats;
    private static final int EXPERIENCE_PER_LEVEL = 100;
    private Experience experience;
    private Inventory inventory;

    private Stamina stamina;
    private Health health;
    private static final int BOOST_COOLDOWN = 60, BOOST_DURATION = 20, THROW_COOLDOWN = 15;
    private AbilityTimer boostTimer, throwTimer;

    // position
    private static final float SIZE = 1;
    private float x, y, z;
    private float vx, vy, vz;
    private float theta, thetaZ;
    private float[] norm, right;
    private boolean zoom;

    private IntersectionMover intersectionMover;
    private IntersectionPicker intersectionPicker;

    private CubeInstancedFaces cubeInstancedFaces;

    // controls
    private KeyControl keyControl;
    private MousePosControl mousePosControl;
    private MouseButtonControl mouseButtonControl;

    public Human(float x, float y, float z, float theta, float thetaZ, IntersectionMover intersectionMover, IntersectionPicker intersectionPicker, KeyControl keyControl, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.theta = theta;
        this.thetaZ = thetaZ;
        norm = new float[2];
        right = new float[2];

        stats = new Stats(RUN_ACC, JUMP_ACC, AIR_ACC, JET_ACC, BOOST_ACC, GLIDE_ACC, GLIDE_DESCENT_ACC, STAMINA, STAMINA_REGEN, STAMINA_RESERVE, STAMINA_RESERVE_REGEN, LIFE, LIFE_REGEN, SHIELD, SHIELD_REGEN, SHIELD_REGEN_DELAY);
        experience = new Experience(EXPERIENCE_PER_LEVEL, stats);
        inventory = new Inventory(16);

        stamina = new Stamina(stats);
        health = new Health(stats);
        boostTimer = new AbilityTimer(BOOST_COOLDOWN, BOOST_DURATION);
        throwTimer = new AbilityTimer(THROW_COOLDOWN, 1);

        this.intersectionMover = intersectionMover;
        this.intersectionPicker = intersectionPicker;

        cubeInstancedFaces = new CubeInstancedFaces(COLOR);

        this.keyControl = keyControl;
        this.mousePosControl = mousePosControl;
        this.mouseButtonControl = mouseButtonControl;
    }

    @Override
    public boolean update(World world) {
        boolean shiftPress = keyControl.isKeyPressed(KeyControl.KEY_SHIFT);
        zoom ^= mousePosControl.isLocked() && mouseButtonControl.isMousePressed(MouseButtonControl.SECONDARY);

        stamina.regen();
        health.regen();

        doRotations(mousePosControl);
        computeAxis();
        doRunningMove(keyControl);

        if (keyControl.isKeyPressed(KeyControl.KEY_SPACE))
            doJump();
        if (keyControl.isKeyDown(KeyControl.KEY_SPACE))
            doJet();

        doBoost(shiftPress);

        vz -= GRAVITY;
        doFriction();

        applyVelocity();

        doThrow(mousePosControl.isLocked() && mouseButtonControl.isMouseDown(MouseButtonControl.PRIMARY), world);

        return false;
    }

    private void doRotations(MousePosControl mousePosControl) {
        theta -= ROTATE_SPEED_MOUSE * mousePosControl.getMoveX();
        thetaZ = MathNumbers.maxMin(thetaZ - ROTATE_SPEED_MOUSE * mousePosControl.getMoveY(), MathAngles.PI / 2, -MathAngles.PI / 2);
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
            acc = stats.boostAcc.getValue();
        else if (!air)
            acc = stats.runAcc.getValue();
        else if (keyControl.isKeyDown(KeyControl.KEY_SHIFT) && stamina.available(Stamina.StaminaCost.GLIDE)) {
            stamina.deplete(Stamina.StaminaCost.GLIDE);
            acc = stats.glideAcc.getValue();
            vz -= stats.glideDescentAcc.getValue();
        } else
            acc = stats.airAcc.getValue();

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
        Stamina.StaminaCost staminaRequired = air ? Stamina.StaminaCost.AIR_JUMP : Stamina.StaminaCost.JUMP;
        if (!stamina.available(staminaRequired))
            return;
        stamina.deplete(staminaRequired);
        vx *= JUMP_MULT;
        vy *= JUMP_MULT;
        vz += stats.jumpAcc.getValue();
    }

    private void doJet() {
        if (!stamina.available(Stamina.StaminaCost.JET))
            return;
        stamina.deplete(Stamina.StaminaCost.JET);
        vz += stats.jetAcc.getValue();
    }

    private void doBoost(boolean shiftPress) {
        boostTimer.update();
        if (shiftPress && boostTimer.ready() && stamina.available(Stamina.StaminaCost.BOOST) && !air) {
            stamina.deplete(Stamina.StaminaCost.BOOST);
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
        Intersection intersection = intersectionMover.find(new float[] {x, y, z}, new float[] {vx, vy, vz}, MathNumbers.magnitude(vx, vy, vz), SIZE);
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

    private void doThrow(boolean primaryDown, World world) {
        throwTimer.update();

        if (primaryDown && throwTimer.ready() && stamina.available(Stamina.StaminaCost.THROW)) {
            stamina.deplete(Stamina.StaminaCost.THROW);
            throwTimer.activate();
            Intersection pick = intersectionPicker.find();
            float topZ = z + SIZE / 2;
            float dx = pick.coordinate.getX() - x;
            float dy = pick.coordinate.getY() - y;
            float dz = pick.coordinate.getZ() - topZ;
            world.addProjectile(new Projectile(x, y, topZ, dx, dy, dz));
        }
    }

    @Override
    public void takeDamage(float amount) {
        health.deplete(amount);
    }

    void experienceAdd(int amount) {
        experience.add(amount);
    }

    void inventoryAdd(Item item) {
        inventory.add(item);
    }

    @Override
    public void draw() {
        if (zoom)
            return;
        cubeInstancedFaces.reset();
        cubeInstancedFaces.add(x, z, -y, theta, thetaZ, SIZE);
        cubeInstancedFaces.doneAdding();
        cubeInstancedFaces.draw();
    }

    // camera getters

    @Override
    public float getFollowX() {
        return x;
    }

    @Override
    public float getFollowY() {
        return z + SIZE / 2;
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

    @Override
    public float[] getFollowNorm() {
        float thetaZCos = MathAngles.cos(thetaZ);
        return new float[] {norm[0] * thetaZCos, MathAngles.sin(thetaZ), -norm[1] * thetaZCos};
    }

    @Override
    public boolean isFollowZoom() {
        return zoom;
    }

    // world getters

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
    public float getSize() {
        return SIZE;
    }

    // ui getters

    public float getStaminaPercent() {
        return stamina.percent();
    }

    public float getStaminaReservePercent() {
        return stamina.percentReserve();
    }

    public float getLifePercent() {
        return health.percentLife();
    }

    public float getShieldPercent() {
        return health.percentShield();
    }

    public int getExperienceLevel() {
        return experience.level();
    }

    public float getExperiencePercent() {
        return experience.percent();
    }

    public Experience getExperience() {
        return experience;
    }

    public Inventory getInventory() {
        return inventory;
    }
}