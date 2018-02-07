package character.monster.attack;

import character.monster.behavior.Timer;

public class MeleeAttack extends MonsterAttack {
    private static final int ATTACK_DELAY = 60;
    private static final float ATTACK_RANGE_HEIGHT = 3;

    private Timer attack, delay;
    private boolean attackInProgress;

    public MeleeAttack() {
        attack = new Timer();
        delay = new Timer();
    }

    @Override
    public void update() {
        if (attackInProgress) {
            delay.update();
            if (delay.done()) {
                attackInProgress = false;
                if (inCubeAoe(attackRange, ATTACK_RANGE_HEIGHT))
                    human.takeDamage(attackDamage);
            }

        } else {
            attack.update();
            if (attack.done() && inCubeAoe(attackRange, ATTACK_RANGE_HEIGHT)) {
                attack.reset(attackTime);
                delay.reset(ATTACK_DELAY);
                attackInProgress = true;
            }
        }
    }

    @Override
    public boolean moveLocked() {
        return attackInProgress;
    }

    @Override
    public void draw() {
        if (attackInProgress) {
            float prog1to0 = delay.getTime() / ATTACK_DELAY;
            float[] color = new float[] {prog1to0, 0, 0, .5f - prog1to0 / 2};
            cubeInstancedFaces.add(monster.getX(), monster.getZ(), -monster.getY(), monster.getTheta(), 0, attackRange * 2, ATTACK_RANGE_HEIGHT * 2, color, false);
            cubeInstancedFaces.add(monster.getX(), monster.getZ(), -monster.getY(), monster.getTheta(), 0, attackRange * 2, ATTACK_RANGE_HEIGHT * 2, color, true);
        }
    }
}