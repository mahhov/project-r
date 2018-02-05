package character.monster.behavior;

import character.Human;
import character.Monster;
import character.monster.attack.MonsterAttack;
import character.monster.motion.MonsterMotion;
import util.MathNumbers;

public class RunawayBehavior extends MonsterBehavior {
    private static final int RUNAWAY_TIME = 300;

    private float hostilitySightDistanceSqr, hostilityDangerDistanceSqr;

    public RunawayBehavior(Monster monster, Human human, MonsterMotion motion, MonsterAttack attack, float hostilitySightDistanceSqr, float hostilityDangerDistanceSqr) {
        super(monster, human, motion, attack);
        this.hostilitySightDistanceSqr = hostilitySightDistanceSqr;
        this.hostilityDangerDistanceSqr = hostilityDangerDistanceSqr;
    }

    @Override
    public void update() {
        float dx = human.getX() - monster.getX();
        float dy = human.getY() - monster.getY();
        float dz = human.getZ() - monster.getZ();
        float distanceSqr = MathNumbers.magnitudeSqr(dx, dy, dz);

        timer.update();

        if (distanceSqr < hostilitySightDistanceSqr || (distanceSqr < hostilityDangerDistanceSqr && human.isDangerous()) || damageTaken) {
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