package character;

import character.monster.MonsterDetails;
import character.monster.MonsterGenerator;
import character.monster.MonsterRewards;
import character.monster.attack.MonsterAttack;
import character.monster.motion.MonsterMotion;
import shape.CubeInstancedFaces;
import util.intersection.IntersectionMover;
import world.World;
import world.particle.SmokeParticle;

public class Monster extends Character {
    // todo ai
    private Human human;
    private MonsterAttack attack;
    private MonsterMotion motion;
    private MonsterRewards rewards;

    public Monster(float x, float y, float z, float theta, float thetaZ, IntersectionMover intersectionMover, Human human, CubeInstancedFaces cubeInstancedFaces, MonsterDetails details) {
        super(x, y, z, theta, thetaZ, details.runAcc, details.airAcc, details.jetAcc, details.size, intersectionMover, createStats(details), details.color, cubeInstancedFaces);
        this.human = human;
        attack = MonsterGenerator.createAttack(this, human, cubeInstancedFaces, details);
        motion = MonsterGenerator.createMotion(this, human, details);
        rewards = new MonsterRewards(human, details);
    }

    private static Stats createStats(MonsterDetails details) {
        return new Stats(details.life, .1f, details.shield, 1, 75);
    }

    @Override
    void doAttack() {
        attack.update(motion.isHostile());
    }

    @Override
    MoveControl getMoveControl(World world) { // todo maybe just pass motion to character and no need to override getMoveControl
        motion.update();
        return motion.moveControl;
    }

    @Override
    boolean die(World world) {
        for (int i = 0; i < 50; i++)
            world.addParticle(new SmokeParticle(getX(), getY(), getZ()));
        rewards.apply();
        return true;
    }

    @Override
    public void takeDamage(float amount) {
        motion.damageTaken();
        super.takeDamage(amount);
    }

    @Override
    public void draw() {
        super.draw();
        attack.draw();
    }
}