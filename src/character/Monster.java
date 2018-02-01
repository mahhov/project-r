package character;

import character.monster.MonsterDetails;
import character.monster.MonsterGenerator;
import character.monster.motion.MonsterMotion;
import item.Coin;
import shape.CubeInstancedFaces;
import util.MathRandom;
import util.intersection.IntersectionMover;
import world.World;

public class Monster extends Character {
    // todo ai
    private static final float CHASE_DISTANCE = 3000, DAMAGE_DISTANCE = 100, DAMAGE_AMOUNT = .2f;

    private Human human;
    private MonsterDetails details;
    private MonsterMotion motion;

    public Monster(float x, float y, float z, float theta, float thetaZ, IntersectionMover intersectionMover, Human human, CubeInstancedFaces cubeInstancedFaces, MonsterDetails details) {
        super(x, y, z, theta, thetaZ, details.runAcc, details.airAcc, details.jetAcc, details.size, intersectionMover, createStats(details), details.color, cubeInstancedFaces);
        this.human = human;
        this.details = details;
        motion = MonsterGenerator.createMotion(this, human, details);
    }

    private static Stats createStats(MonsterDetails details) {
        return new Stats(details.life, .1f, details.shield, 1, 75);
    }

    @Override
    MoveControl getMoveControl(World world) { // todo maybe just pass motion to character and no need to override getMoveControl
        motion.update();
        return motion.moveControl;
    }

    @Override
    boolean die() {
        human.inventoryAdd(new Coin(MathRandom.random(5, 15)));
        human.experienceAdd(30);
        return true;
    }

    @Override
    public void takeDamage(float amount) {
        motion.damageTaken();
        super.takeDamage(amount);
    }
}