package character.monster.motion;

import character.Human;
import character.Monster;
import character.MoveControl;
import util.MathAngles;
import util.MathRandom;

public class MonsterMotion {
    Monster monster;
    Human human;
    public final MoveControl moveControl;

    float wanderSpeed;
    float flyHeight;

    MonsterMotion() {
        this.moveControl = new MoveControl();
    }

    public void update() {
    }

    public void setBase(Monster monster, Human human, float wanderSpeed) {
        this.monster = monster;
        this.human = human;
        this.wanderSpeed = wanderSpeed;
    }

    public void setFlyHeight(int flyHeight) {
        this.flyHeight = flyHeight;
    }

    void wander() {
        float angle = MathRandom.random(0, MathAngles.PI * 2);
        moveControl.dx = MathAngles.cos(angle);
        moveControl.dy = MathAngles.sin(angle);
        moveControl.speed = wanderSpeed;
        moveControl.theta = (float) Math.atan2(moveControl.dy, moveControl.dx);
    }
}