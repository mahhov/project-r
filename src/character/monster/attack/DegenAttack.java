package character.monster.attack;

import util.MathAngles;

public class DegenAttack extends MonsterAttack {
    private static final float[] COLOR = new float[] {1, 0, 0, .5f};

    @Override
    public void update(boolean hostile) {
        if (!(this.hostile = hostile))
            return;

        float dx = human.getX() - monster.getX();
        float dy = human.getY() - monster.getY();
        float dz = human.getZ() - monster.getZ();
        float theta = monster.getTheta();

        float[] norm = new float[2];
        norm[0] = -MathAngles.sin(theta);
        norm[1] = MathAngles.cos(theta);

        float dot1 = norm[0] * dx + norm[1] * dy;
        float dot2 = norm[1] * dx - norm[0] * dy;

        if (dot1 > -attackAoe && dot1 < attackAoe && dot2 > -attackAoe && dot2 < attackAoe && dz > -attackAoe && dz < attackAoe)
            human.takeDamage(attackDamage);
    }

    @Override
    public void draw() {
        if (!hostile)
            return;
        cubeInstancedFaces.add(monster.getX(), monster.getZ(), -monster.getY(), monster.getTheta(), 0, attackAoe * 2, COLOR);
        cubeInstancedFaces.add(monster.getX(), monster.getZ(), -monster.getY(), monster.getTheta(), 0, attackAoe * 2, COLOR, true);
    }
}