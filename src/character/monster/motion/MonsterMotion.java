package character.monster.motion;

import character.Human;
import character.Monster;
import character.MoveControl;
import util.MathAngles;
import util.MathRandom;

public class MonsterMotion {
    private static final float FLY_SMOOTH_MULT = .1f;

    Monster monster;
    Human human;
    public final MoveControl moveControl;

    private float wanderSpeed;
    private float flyHeight;
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
        this.flyHeight = flyHeight;
    }

    public void setHostilityParams(float hostilitySpeed, float hostilitySightDistance, float hostilityDangerDistance) {
        this.hostilitySpeed = hostilitySpeed;
        hostilitySightDistanceSqr = hostilitySightDistance * hostilitySightDistance;
        hostilityDangerDistanceSqr = hostilityDangerDistance * hostilityDangerDistance;
    }

    void wander() {
        float angle = MathRandom.random(0, MathAngles.PI * 2);
        moveControl.dx = MathAngles.cos(angle);
        moveControl.dy = MathAngles.sin(angle);
        moveControl.speed = wanderSpeed;
        moveControl.theta = (float) Math.atan2(moveControl.dy, moveControl.dx);
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
        moveControl.dz = deltaZ > 0 ? deltaZ * FLY_SMOOTH_MULT : 0;
    }
}