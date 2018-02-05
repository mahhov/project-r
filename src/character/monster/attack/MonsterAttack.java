package character.monster.attack;

import character.Human;
import character.Monster;
import shape.CubeInstancedFaces;

public class MonsterAttack {
    Monster monster;
    Human human;
    CubeInstancedFaces cubeInstancedFaces;

    float attackSpeed, attackDamage, attackRange, attackSize, attackAoe, attackAoeSqr;

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
        attackAoeSqr = attackAoe * attackAoe;
    }

    public void draw() {
    }
}