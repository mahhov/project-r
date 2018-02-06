package character.monster.attack;

import character.monster.behavior.Timer;

public class MeleeAttack extends MonsterAttack {
    private static final int ATTACK_DELAY = 60;

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
                if (inCubeAoe(attackRange))
                    human.takeDamage(attackDamage);
            }

        } else {
            attack.update();
            if (attack.done() && inCubeAoe(attackRange)) {
                attack.reset(100); // todo constant
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
            cubeInstancedFaces.add(monster.getX(), monster.getZ(), -monster.getY(), monster.getTheta(), 0, attackRange * 2, color);
            cubeInstancedFaces.add(monster.getX(), monster.getZ(), -monster.getY(), monster.getTheta(), 0, attackRange * 2, color, true);
        }
    }
}

// todo behavior approach distance based on attack range