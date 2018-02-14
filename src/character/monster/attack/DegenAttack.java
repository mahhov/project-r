package character.monster.attack;

public class DegenAttack extends Attack {
    private static final float[] COLOR = new float[] {1, 0, 0, .5f};
    private float size;

    @Override
    public void setParams(float attackSpeed, float attackDamage, float attackRange, float attackSize, float attackAoe) {
        super.setParams(attackSpeed, attackDamage, attackRange, attackSize, attackAoe);
        size = attackAoe * 2;
    }

    @Override
    public void update() {
        if (inCubeAoe(attackAoe, attackAoe))
            human.takeDamage(attackDamage);
    }

    public float getDistanceRequired() {
        return attackAoe / 2;
    }

    @Override
    public void draw() {
        cubeInstancedFaces.add(monster.getX(), monster.getZ(), -monster.getY(), monster.getTheta(), 0, size, size, size, COLOR, false);
        cubeInstancedFaces.add(monster.getX(), monster.getZ(), -monster.getY(), monster.getTheta(), 0, size, size, size, COLOR, true);
    }
}