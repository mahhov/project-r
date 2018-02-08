package character.monster.behavior;

import character.Human;
import character.Monster;
import character.monster.attack.Attack;
import character.monster.motion.Motion;
import util.MathNumbers;

public class RunawayBehavior extends Behavior {
    private static final int RUNAWAY_TIME = 300;

    private float sightDistanceSqr, dangerDistanceSqr;

    public RunawayBehavior(Monster monster, Human human, Motion motion, Attack attack, float sightDistance, float dangerDistance) {
        super(monster, human, motion, attack);
        sightDistanceSqr = sightDistance * sightDistance;
        dangerDistanceSqr = dangerDistance * dangerDistance;
    }

    @Override
    public void update() {
        float dx = human.getX() - monster.getX();
        float dy = human.getY() - monster.getY();
        float dz = human.getZ() - monster.getZ();
        float distanceSqr = MathNumbers.magnitudeSqr(dx, dy, dz);

        timer.update();

        if (distanceSqr < sightDistanceSqr || (distanceSqr < dangerDistanceSqr && human.isDangerous()) || damageTaken) {
            timer.reset(RUNAWAY_TIME);
            motion.run(-dx, -dy);
            damageTaken = false;
        }

        if (timer.done()) {
            wanderOrStand();
        }

        motion.jet();
    }
}