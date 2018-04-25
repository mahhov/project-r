package character;

import character.monster.MonsterDetails;
import character.monster.MonsterGenerator;
import character.monster.MonsterRewards;
import character.monster.behavior.Behavior;
import shape.CubeInstancedFaces;
import util.intersection.IntersectionMover;
import world.World;
import world.particle.SmokeParticle;

public class Monster extends Character {
    private MonsterDetails details;
    private Behavior behavior;
    private MonsterRewards rewards;

    public Monster(float x, float y, float z, float theta, float thetaZ, MonsterDetails details) {
        super(x, y, z, theta, thetaZ, details.runAcc, details.airAcc, details.jetAcc, details.size, createStats(details), details.color);
        this.details = details;
    }

    public void connectWorld(World world, Human human, IntersectionMover intersectionMover, CubeInstancedFaces cubeInstancedFaces) {
        super.connectWorld(world, intersectionMover, details.modelData, cubeInstancedFaces);
        behavior = MonsterGenerator.createBehavior(this, human, cubeInstancedFaces, details);
        rewards = new MonsterRewards(human, details);
    }

    private static Stats createStats(MonsterDetails details) {
        return new Stats(details.life, .1f, details.shield, 1, 75);
    }

    void updateBehavior() {
        behavior.update();
    }

    @Override
    MoveControl getMoveControl() {
        return behavior.getMoveControl();
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
        super.takeDamage(amount);
        behavior.damageTaken();
    }

    @Override
    public void draw() {
        super.draw();
        behavior.draw();
    }
}
