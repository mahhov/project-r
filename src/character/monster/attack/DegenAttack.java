package character.monster.attack;

import util.MathNumbers;

public class DegenAttack extends MonsterAttack {
    public void update() { // todo apply params
        float dx = human.getX() - monster.getX();
        float dy = human.getY() - monster.getY();
        float dz = human.getZ() - monster.getZ();
        float distanceSqr = MathNumbers.magnitudeSqr(dx, dy, dz);

        if (distanceSqr < 100)
            human.takeDamage(1);
    }
}