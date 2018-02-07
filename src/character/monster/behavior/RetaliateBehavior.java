package character.monster.behavior;

import character.Human;
import character.Monster;
import character.monster.attack.MonsterAttack;
import character.monster.motion.MonsterMotion;
import util.MathNumbers;

public class RetaliateBehavior extends MonsterBehavior {
    private static final int RETALIATE_TIME = 300;
    private static final float RETALIATE_FORGET_DISTANCE_SQR = 100;
    private static final float RETALIATE_DISTANCE_SPEED_MULT = .05f;

    public RetaliateBehavior(Monster monster, Human human, MonsterMotion motion, MonsterAttack attack) {
        super(monster, human, motion, attack);
    }

    @Override
    public void update() {
        timer.update();

        if (damageTaken) {
            timer.reset(RETALIATE_TIME);
            state = State.HOSTILE;
            damageTaken = false;
        }

        if (timer.done()) {
            wanderOrStand();
            state = State.PASSIVE;

        } else if (state == State.HOSTILE) {
            float dx = human.getX() - monster.getX();
            float dy = human.getY() - monster.getY();
            float flatDistanceSqr = MathNumbers.magnitudeSqr(dx, dy);
            float attackDistance = attack.getDistanceRequired();

            if (flatDistanceSqr < attackDistance || attack.moveLocked())
                motion.stand();
            else
                motion.run(dx, dy, (flatDistanceSqr - attackDistance) * RETALIATE_DISTANCE_SPEED_MULT);

            if (flatDistanceSqr < RETALIATE_FORGET_DISTANCE_SQR)
                timer.reset(RETALIATE_TIME);
            attack.update();
        }

        motion.jet();
    }

    @Override
    public void draw() {
        if (state == State.HOSTILE)
            attack.draw();
    }
}