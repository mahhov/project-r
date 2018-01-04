package character;

import shape.CubeInstancedFaces;
import util.IntersectionFinder;
import util.MathNumbers;
import world.World;

public class Monster extends Character {
    // ai
    private static final int CHASE_DISTANCE = 3000, DAMAGE_DISTANCE = 100, DAMAGE_AMOUNT = 2;

    public static final float[] COLOR = new float[] {1, 0, 0};
    private static final float LIFE = 10, LIFE_REGEN = 0, SHIELD = 0, SHIELD_REGEN = 0;

    private Human human;
    private MoveControl moveControl;

    public Monster(float x, float y, float z, float theta, float thetaZ, IntersectionFinder intersectionFinder, Human human, CubeInstancedFaces cubeInstancedFaces) {
        super(x, y, z, theta, thetaZ, intersectionFinder, LIFE, LIFE_REGEN, SHIELD, SHIELD_REGEN, COLOR, cubeInstancedFaces);
        this.human = human;
        moveControl = new MoveControl();
    }

    @Override
    public void update(World world) {
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

        move(moveControl);
    }
}