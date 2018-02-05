package character.monster.behavior;

import character.Human;
import character.Monster;
import character.monster.attack.MonsterAttack;
import character.monster.motion.MonsterMotion;
import util.MathNumbers;

public class AggressiveBehavior extends MonsterBehavior {
    private static final int RETALIATE_TIME = 300;
    private static final float RETALIATE_DISTANCE_SQR = 10;
    private static final float RETALIATE_DISTANCE_SPEED_MULT = .05f;

    private float detectionRangeSqr;

    public AggressiveBehavior(Monster monster, Human human, MonsterMotion motion, MonsterAttack attack, float detectionRange) {
        super(monster, human, motion, attack);
        detectionRangeSqr = detectionRange * detectionRange;
    }

    @Override
    public void update() {
        float dx = human.getX() - monster.getX();
        float dy = human.getY() - monster.getY();
        float dz = human.getZ() - monster.getZ();
        float distanceSqr = MathNumbers.magnitudeSqr(dx, dy, dz);

        timer.update();

        if (distanceSqr < detectionRangeSqr || damageTaken) {
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
            if (flatDistanceSqr < detectionRangeSqr * 4)
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