package character.monster.attack;

import character.Human;
import character.Monster;

public class MonsterAttack {
    Monster monster;
    Human human;

    MonsterAttack() {
    }

    public void update() {
    }

    public void setBase(Monster monster, Human human) {
        this.monster = monster;
        this.human = human;
    }

    public void setParams() {
    }
}