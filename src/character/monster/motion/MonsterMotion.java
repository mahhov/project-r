package character.monster.motion;

import character.Human;
import character.Monster;
import character.MoveControl;

public class MonsterMotion {
    final Monster monster;
    final Human human;
    public final MoveControl moveControl;

    public MonsterMotion(Monster monster, Human human) {
        this.monster = monster;
        this.human = human;
        this.moveControl = new MoveControl();
    }

    public void update() {
    }
}