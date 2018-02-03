package character.monster.motion;

import character.Character;
import character.Human;
import character.Monster;
import character.MoveControl;
import util.MathAngles;
import util.MathRandom;

public class MonsterMotion {
    private static final float FLY_SMOOTH_MULT = .001f;

    Monster monster;
    Human human;
    public final MoveControl moveControl;

    private float wanderSpeed;
    private float avgFlyHeight, flyHeight;
    float hostilitySpeed;
    float hostilitySightDistanceSqr, hostilityDangerDistanceSqr;

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
        avgFlyHeight = flyHeight;
    }

    public void setParams(float hostilitySpeed, float hostilitySightDistance, float hostilityDangerDistance) {
        this.hostilitySpeed = hostilitySpeed;
        hostilitySightDistanceSqr = hostilitySightDistance * hostilitySightDistance;
        hostilityDangerDistanceSqr = hostilityDangerDistance * hostilityDangerDistance;
    }

    void wander() {
        float angle = MathRandom.randomAngle();
        moveControl.dx = MathAngles.cos(angle);
        moveControl.dy = MathAngles.sin(angle);
        moveControl.speed = wanderSpeed;
        moveControl.theta = (float) Math.atan2(moveControl.dy, moveControl.dx);
        flyHeight = MathRandom.random(avgFlyHeight, avgFlyHeight * 1.1f);
    }

    void run(float dx, float dy, float speed) {
        moveControl.dx = dx;
        moveControl.dy = dy;
        moveControl.speed = speed;
        moveControl.theta = (float) Math.atan2(dy, dx);
    }

    void stand() {
        moveControl.dx = 0;
        moveControl.dy = 0;
        moveControl.speed = 0;
    }

    void jet() {
        float deltaZ = flyHeight - monster.getZ();
        moveControl.dz = Character.GRAVITY + deltaZ * FLY_SMOOTH_MULT;
    }

    public void damageTaken() {
    }
}