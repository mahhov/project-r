package character.monster.motion;

import character.Character;
import character.Monster;
import character.MoveControl;
import model.Model;
import model.animation.AnimationSet;
import util.math.MathAngles;
import util.math.MathNumbers;
import util.math.MathRandom;
import world.WorldElement;

public class Motion {
    private static final float FLY_SMOOTH_MULT = .001f;

    private Monster monster;
    public final MoveControl moveControl;
    private Model model;

    private float wanderSpeed, runSpeed;
    private float avgFlyHeight, flyHeight;

    public Motion(Monster monster) {
        this.monster = monster;
        moveControl = new MoveControl();
        model = monster.getModel();
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
        model.animate(AnimationSet.AnimationType.STAND);
    }

    public void lookAt(WorldElement target) {
        float dx = target.getX() - monster.getX();
        float dy = target.getY() - monster.getY();
        setTheta(dy, dx);
        model.animate(AnimationSet.AnimationType.STAND);
    }

    public void wander() {
        float angle = MathRandom.randomAngle();
        moveControl.dx = MathAngles.cos(angle);
        moveControl.dy = MathAngles.sin(angle);
        moveControl.speed = wanderSpeed;
        setTheta(moveControl.dy, moveControl.dx);
        flyHeight = MathRandom.random(avgFlyHeight, avgFlyHeight * 1.1f);
        model.animate(AnimationSet.AnimationType.WALK);
    }

    public void run(float dx, float dy) {
        moveControl.dx = dx;
        moveControl.dy = dy;
        moveControl.speed = runSpeed;
        setTheta(dy, dx);
        model.animate(AnimationSet.AnimationType.WALK);
    }

    public void run(float dx, float dy, float maxSpeed) {
        moveControl.dx = dx;
        moveControl.dy = dy;
        moveControl.speed = MathNumbers.min(runSpeed, maxSpeed);
        setTheta(dy, dx);
        model.animate(AnimationSet.AnimationType.WALK);
    }

    public void jet() {
        float deltaZ = flyHeight - monster.getZ();
        moveControl.dz = Character.GRAVITY + deltaZ * FLY_SMOOTH_MULT;
    }

    private void setTheta(float dy, float dx) {
        moveControl.theta = (float) Math.atan2(dy, dx) - MathAngles.PI_HALF;
    }
}