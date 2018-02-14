package character.monster.attack;

import character.Human;
import character.Monster;
import shape.CubeInstancedFaces;
import util.math.MathAngles;

public class Attack {
    private static final int BASE_ATTACK_TIME = 100 * 100;

    Monster monster;
    Human human;
    CubeInstancedFaces cubeInstancedFaces;

    float attackSpeed, attackDamage, attackRange, attackSize, attackAoe;
    int attackTime;

    public void update() {
    }

    public void setBase(Monster monster, Human human, CubeInstancedFaces cubeInstancedFaces) {
        this.monster = monster;
        this.human = human;
        this.cubeInstancedFaces = cubeInstancedFaces;
    }

    public void setParams(float attackSpeed, float attackDamage, float attackRange, float attackSize, float attackAoe) {
        this.attackSpeed = attackSpeed;
        this.attackDamage = attackDamage;
        this.attackRange = attackRange;
        this.attackSize = attackSize;
        this.attackAoe = attackAoe;
        attackTime = (int) (Attack.BASE_ATTACK_TIME / attackSpeed) + 1;
    }

    public boolean moveLocked() {
        return false;
    }

    public float getDistanceRequired() {
        return attackRange / 2;
    }

    public void draw() {
    }

    boolean inCubeAoe(float cubeHalfSize, float cubeHalfHeight) {
        float dx = human.getX() - monster.getX();
        float dy = human.getY() - monster.getY();
        float dz = human.getZ() - monster.getZ();
        float theta = monster.getTheta();
        float[] norm = MathAngles.norm(theta);

        float dot1 = norm[0] * dx + norm[1] * dy;
        float dot2 = norm[1] * dx - norm[0] * dy;

        return dot1 > -cubeHalfSize && dot1 < cubeHalfSize && dot2 > -cubeHalfSize && dot2 < cubeHalfSize && dz > -cubeHalfHeight && dz < cubeHalfHeight;
    }
}