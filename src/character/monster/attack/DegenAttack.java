package character.monster.attack;

import util.MathNumbers;

public class DegenAttack extends MonsterAttack {
    @Override
    public void update(boolean hostile) {
        if (!hostile)
            return;

        float dx = human.getX() - monster.getX();
        float dy = human.getY() - monster.getY();
        float dz = human.getZ() - monster.getZ();
        float distanceSqr = MathNumbers.magnitudeSqr(dx, dy, dz);

        if (distanceSqr < attackAoeSqr)
            human.takeDamage(attackDamage);
    }
}