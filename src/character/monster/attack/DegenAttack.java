package character.monster.attack;

public class DegenAttack extends MonsterAttack {
    private static final float[] COLOR = new float[] {1, 0, 0, .5f};

    @Override
    public void update() {
        if (inCubeAoe(attackAoe))
            human.takeDamage(attackDamage);
    }

    @Override
    public void draw() {
        cubeInstancedFaces.add(monster.getX(), monster.getZ(), -monster.getY(), monster.getTheta(), 0, attackAoe * 2, COLOR);
        cubeInstancedFaces.add(monster.getX(), monster.getZ(), -monster.getY(), monster.getTheta(), 0, attackAoe * 2, COLOR, true);
    }
}