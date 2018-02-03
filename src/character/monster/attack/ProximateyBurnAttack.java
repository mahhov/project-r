package character.monster.attack;

import util.MathNumbers;

public class ProximateyBurnAttack extends MonsterAttack {
    public void update() {
        float dx = human.getX() - monster.getX();
        float dy = human.getY() - monster.getY();
        float dz = human.getZ() - monster.getZ();
        float distanceSqr = MathNumbers.magnitudeSqr(dx, dy, dz);

        if (distanceSqr < 100)
            human.takeDamage(1);
    }
}