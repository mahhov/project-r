package character.monster.attack;

public class DegenAttack extends Attack {
    private static final float[] COLOR = new float[] {1, 0, 0, .5f};

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
        cubeInstancedFaces.add(monster.getX(), monster.getZ(), -monster.getY(), monster.getTheta(), 0, attackAoe * 2, attackAoe * 2, COLOR, false);
        cubeInstancedFaces.add(monster.getX(), monster.getZ(), -monster.getY(), monster.getTheta(), 0, attackAoe * 2, attackAoe * 2, COLOR, true);
    }
}