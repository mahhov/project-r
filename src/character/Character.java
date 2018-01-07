package character;

import geometry.CoordinateI3;
import shape.CubeInstancedFaces;
import util.LList;
import util.MathNumbers;
import util.intersection.Intersection;
import util.intersection.IntersectionMover;
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
    private IntersectionMover intersectionMover;

    private CubeInstancedFaces cubeInstancedFaces;

    private CoordinateI3 worldCoordinate;
    private LList<WorldElement>.Node worldElementNode;

    Character(float x, float y, float z, float theta, float thetaZ, IntersectionMover intersectionMover, float life, float lifeRegen, float shield, float shieldRegen, float[] color, CubeInstancedFaces cubeInstancedFaces) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.theta = theta;
        this.thetaZ = thetaZ;
        this.intersectionMover = intersectionMover;

        this.life = new Life(life, lifeRegen, shield, shieldRegen);

        this.cubeInstancedFaces = cubeInstancedFaces;
    }

    @Override
    public boolean update(World world) {
        removeFromWorld(world);
        if (life.depleted())
            return true;
        move(createMoveControl(world));
        addToWorld(world);
        return false;
    }

    private void removeFromWorld(World world) {
        if (worldElementNode != null)
            world.removeDynamicElement(worldCoordinate, worldElementNode);
    }

    private void addToWorld(World world) {
        worldCoordinate = new CoordinateI3((int) x, (int) y, (int) z);
        worldElementNode = world.addDynamicElement(worldCoordinate, this);
    }

    MoveControl createMoveControl(World world) {
        return new MoveControl();
    }

    private void move(MoveControl moveControl) {
        life.regen();

        doRotations(moveControl);
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

    private void doRunningMove(MoveControl moveControl) {
        float acc = air ? AIR_ACC : RUN_ACC;

        float[] dir = MathNumbers.setMagnitude(moveControl.dx, moveControl.dy, 0, acc);
        vx += dir[0];
        vy += dir[1];
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
        Intersection intersection = intersectionMover.find(new float[] {x, y, z}, new float[] {vx, vy, vz}, MathNumbers.magnitude(vx, vy, vz), 1);
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
    public void takeDamage(float amount) {
        life.deplete(amount);
    }

    @Override
    public void draw() {
        cubeInstancedFaces.add(x, z, -y, theta, thetaZ);
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
        return 1;
    }
}