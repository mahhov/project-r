package character.monster.motion;

import character.Character;
import character.Monster;
import character.MoveControl;
import util.MathAngles;
import util.MathNumbers;
import util.MathRandom;
import world.WorldElement;

public class MonsterMotion {
    private static final float FLY_SMOOTH_MULT = .001f;

    private Monster monster;
    public final MoveControl moveControl;

    private float wanderSpeed, runSpeed;
    private float avgFlyHeight, flyHeight;

    public MonsterMotion(Monster monster) {
        this.monster = monster;
        moveControl = new MoveControl();
    }

    public void setSpeeds(float wanderSpeed, float runSpeed) {
        this.wanderSpeed = wanderSpeed;
        this.runSpeed = runSpeed;
    }

    public void setFlyHeight(int flyHeight) {
        avgFlyHeight = flyHeight;
    }

    public void stand() {
        moveControl.dx = 0;
        moveControl.dy = 0;
        moveControl.speed = 0;
    }

    public void lookAt(WorldElement target) {
        float dx = target.getX() - monster.getX();
        float dy = target.getY() - monster.getY();
        moveControl.theta = (float) Math.atan2(dy, dx);
    }

    public void wander() {
        float angle = MathRandom.randomAngle();
        moveControl.dx = MathAngles.cos(angle);
        moveControl.dy = MathAngles.sin(angle);
        moveControl.speed = wanderSpeed;
        moveControl.theta = (float) Math.atan2(moveControl.dy, moveControl.dx);
        flyHeight = MathRandom.random(avgFlyHeight, avgFlyHeight * 1.1f);
    }

    public void run(float dx, float dy) {
        moveControl.dx = dx;
        moveControl.dy = dy;
        moveControl.speed = runSpeed;
        moveControl.theta = (float) Math.atan2(dy, dx);
    }

    public void run(float dx, float dy, float maxSpeed) {
        moveControl.dx = dx;
        moveControl.dy = dy;
        moveControl.speed = MathNumbers.min(runSpeed, maxSpeed);
        moveControl.theta = (float) Math.atan2(dy, dx);
    }

    public void jet() {
        float deltaZ = flyHeight - monster.getZ();
        moveControl.dz = Character.GRAVITY + deltaZ * FLY_SMOOTH_MULT;
    }
}