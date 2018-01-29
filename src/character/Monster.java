package character;

import character.monster.MonsterDetails;
import character.monster.RunawayOnSightMonsterMotion;
import item.Coin;
import shape.CubeInstancedFaces;
import util.MathRandom;
import util.intersection.IntersectionMover;
import world.World;

public class Monster extends Character {
    // todo ai
    private static final float CHASE_DISTANCE = 3000, DAMAGE_DISTANCE = 100, DAMAGE_AMOUNT = .2f;

    public static final float[] COLOR = new float[] {1, 0, 0};

    private Human human;
    private MonsterDetails monsterDetails;
    private RunawayOnSightMonsterMotion motion;

    public Monster(float x, float y, float z, float theta, float thetaZ, IntersectionMover intersectionMover, Human human, CubeInstancedFaces cubeInstancedFaces, MonsterDetails monsterDetails) {
        super(x, y, z, theta, thetaZ, monsterDetails.size, intersectionMover, createStats(monsterDetails), COLOR, cubeInstancedFaces);
        this.human = human;
        this.monsterDetails = monsterDetails;
        motion = new RunawayOnSightMonsterMotion(this, human, monsterDetails.movementSpeed, monsterDetails.hostilitySpeed, monsterDetails.hostilityDistance);
    }

    private static Stats createStats(MonsterDetails monsterDetails) {
        return new Stats(monsterDetails.life, .1f, monsterDetails.shield, 1, 75);
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
}