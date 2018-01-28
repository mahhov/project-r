package character;

import character.monster.MonsterDetails;
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

    private Human human;
    private MoveControl moveControl;
    private MonsterDetails monsterDetails;

    public Monster(float x, float y, float z, float theta, float thetaZ, IntersectionMover intersectionMover, Human human, CubeInstancedFaces cubeInstancedFaces, MonsterDetails monsterDetails) {
        super(x, y, z, theta, thetaZ, monsterDetails.size, monsterDetails.movementSpeed, intersectionMover, createStats(monsterDetails), COLOR, cubeInstancedFaces);
        this.human = human;
        moveControl = new MoveControl();
        this.monsterDetails = monsterDetails;
    }

    private static Stats createStats(MonsterDetails monsterDetails) {
        return new Stats(monsterDetails.life, .1f, monsterDetails.shield, 1, 75);
    }

    @Override
    MoveControl getMoveControl(World world) {
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