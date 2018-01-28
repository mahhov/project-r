package character;

import item.Coin;
import shape.CubeInstancedFaces;
import util.MathNumbers;
import util.MathRandom;
import util.intersection.IntersectionMover;
import world.World;

public class Monster extends Character {
    // todo ai
    private static final float CHASE_DISTANCE = 3000, DAMAGE_DISTANCE = 100, DAMAGE_AMOUNT = .2f;

    public static final float[] COLOR = new float[] {1, 0, 0};
    private static final float LIFE = 20, LIFE_REGEN = .1f, SHIELD = 20, SHIELD_REGEN = 1f;
    private static final int SHIELD_REGEN_DELAY = 75;

    private Human human;
    private MoveControl moveControl;

    public Monster(float x, float y, float z, float theta, float thetaZ, IntersectionMover intersectionMover, Human human, CubeInstancedFaces cubeInstancedFaces) {
        super(x, y, z, theta, thetaZ, intersectionMover, new Stats(LIFE, LIFE_REGEN, SHIELD, SHIELD_REGEN, SHIELD_REGEN_DELAY), COLOR, cubeInstancedFaces);
        this.human = human;
        moveControl = new MoveControl();
    }

    @Override
    MoveControl createMoveControl(World world) {
        float dx = human.getX() - getX();
        float dy = human.getY() - getY();
        float dz = human.getZ() - getZ();
        float distanceSqr = MathNumbers.magnitudeSqr(dx, dy, dz);

        if (distanceSqr <= DAMAGE_DISTANCE) {
            human.takeDamage(DAMAGE_AMOUNT);
        }

        if (distanceSqr > DAMAGE_DISTANCE && distanceSqr < CHASE_DISTANCE) {
            moveControl.dx = dx;
            moveControl.dy = dy;
        } else {
            moveControl.dx = 0;
            moveControl.dy = 0;
        }

        if (distanceSqr < CHASE_DISTANCE)
            moveControl.theta = (float) Math.atan2(dy, dx);

        return moveControl;
    }

    @Override
    boolean die() {
        human.inventoryAdd(new Coin(MathRandom.random(5, 15)));
        human.experienceAdd(30);
        return true;
    }
}