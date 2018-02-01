package character;

import geometry.CoordinateI3;
import shape.CubeInstancedFaces;
import util.LList;
import util.MathNumbers;
import util.intersection.Intersection;
import util.intersection.IntersectionMover;
import world.World;
import world.WorldElement;

public abstract class Character implements WorldElement { // todo support human movement
    public static final int WORLD_ELEMENT_ID = 1;

    // mobility
    public static final float GRAVITY = .1f;
    private static final float FRICTION = 0.8f, AIR_FRICTION = 0.97f, JUMP_MULT = 1;
    private static final float JUMP_ACC = 1f;
    private boolean air;

    // position
    private float x, y, z;
    private float vx, vy, vz;
    private float theta, thetaZ;
    private float runAcc, airAcc, jetAcc;
    private float size;
    private IntersectionMover intersectionMover;

    private Health health;

    private float[] color;

    private CubeInstancedFaces cubeInstancedFaces;

    private CoordinateI3 worldCoordinate;
    private LList<WorldElement>.Node worldElementNode;

    Character(float x, float y, float z, float theta, float thetaZ, float runAcc, float airAcc, float jetAcc, float size, IntersectionMover intersectionMover, Stats stats, float[] color, CubeInstancedFaces cubeInstancedFaces) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.theta = theta;
        this.thetaZ = thetaZ;
        this.runAcc = runAcc;
        this.airAcc = airAcc;
        this.jetAcc = jetAcc;
        this.size = size;
        this.intersectionMover = intersectionMover;

        this.health = new Health(stats);

        this.color = color;

        this.cubeInstancedFaces = cubeInstancedFaces;
    }

    @Override
    public boolean update(World world) {
        if (health.depleted()) {
            world.removeDynamicElement(worldCoordinate, worldElementNode);
            return die();
        }
        move(getMoveControl(world));
        if (world.movable((int) x, (int) y, (int) z))
            moveInWorld(world);
        return false;
    }

    private void moveInWorld(World world) {
        CoordinateI3 newWorldCoordinate = new CoordinateI3((int) x, (int) y, (int) z);
        if (worldElementNode != null)
            worldElementNode = world.moveDynamicElement(worldCoordinate, newWorldCoordinate, worldElementNode);
        else
            worldElementNode = world.addDynamicElement(newWorldCoordinate, this);
        worldCoordinate = newWorldCoordinate;
    }

    MoveControl getMoveControl(World world) {
        return new MoveControl();
    }

    private void move(MoveControl moveControl) {
        health.regen();

        doRotations(moveControl);
        doRunningMove(moveControl);

        if (moveControl.jump && !air)
            doJump();

        if (moveControl.dz > 0)
            doJet(moveControl);

        vz -= GRAVITY;
        doFriction();

        applyVelocity();
    }

    private void doRotations(MoveControl moveControl) {
        theta = moveControl.theta;
        thetaZ = moveControl.thetaZ;
    }

    private void doRunningMove(MoveControl moveControl) {
        float acc = air ? airAcc : runAcc;

        float[] dir = MathNumbers.setMagnitude(moveControl.dx, moveControl.dy, 0, moveControl.speed * acc);
        vx += dir[0];
        vy += dir[1];
    }

    private void doJump() {
        vx *= JUMP_MULT;
        vy *= JUMP_MULT;
        vz += JUMP_ACC;
    }

    private void doJet(MoveControl moveControl) {
        vz += MathNumbers.min(moveControl.dz, jetAcc);
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
        Intersection intersection = intersectionMover.find(new float[] {x, y, z}, new float[] {vx, vy, vz}, MathNumbers.magnitude(vx, vy, vz), size);
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

    boolean die() {
        return true;
    }

    @Override
    public void takeDamage(float amount) {
        health.deplete(amount);
    }

    @Override
    public void draw() {
        cubeInstancedFaces.add(x, z, -y, theta, thetaZ, size, color);
    }

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
        return size;
    }

    public float getLifePercent() {
        return health.percentLife();
    }

    public float getShieldPercent() {
        return health.percentShield();
    }

    public int getId() {
        return WORLD_ELEMENT_ID;
    }
}