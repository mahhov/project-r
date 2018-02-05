package character.monster.behavior;

import character.Human;
import character.Monster;
import character.monster.attack.MonsterAttack;
import character.monster.motion.MonsterMotion;
import util.MathNumbers;

public class RetaliateBehavior extends MonsterBehavior {
    private static final int RETALIATE_TIME = 300;
    private static final float RETALIATE_DISTANCE_SQR = 10, RETALIATE_FORGET_DISTANCE_SQR = 100;
    private static final float RETALIATE_DISTANCE_SPEED_MULT = .05f;

    private float hostilitySightDistanceSqr, hostilityDangerDistanceSqr;

    public RetaliateBehavior(Monster monster, Human human, MonsterMotion motion, MonsterAttack attack, float hostilitySightDistanceSqr, float hostilityDangerDistanceSqr) {
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
            timer.reset(RETALIATE_TIME);
            state = State.HOSTILE;
            damageTaken = false;
        }

        if (timer.done())
            wanderOrStand();

        else if (state == State.HOSTILE) {
            float flatDistanceSqr = MathNumbers.magnitudeSqr(dx, dy);
            if (flatDistanceSqr < RETALIATE_DISTANCE_SQR)
                motion.stand();
            else
                motion.run(dx, dy, flatDistanceSqr * RETALIATE_DISTANCE_SPEED_MULT);
            if (flatDistanceSqr < RETALIATE_FORGET_DISTANCE_SQR)
                timer.reset(RETALIATE_TIME);
        }

        motion.jet();

        if (state == State.HOSTILE)
            attack.update();
    }

    @Override
    public void draw() {
        if (state == State.HOSTILE)
            attack.draw();
    }
}