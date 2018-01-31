package character.monster.motion;

import util.MathNumbers;
import util.MathRandom;

public class RunawayMotion extends MonsterMotion {
    private static final int RUNAWAY_TIME = 300, WANDER_TIME = 100;
    private static final float WANDER_PROB = .7f;
    private static final float FLY_SMOOTH_MULT = .1f;

    private float runawaySpeed;
    private float runawaySightDistanceSqr, runawayDangerDistanceSqr;
    private Timer runawayTimer;

    public RunawayMotion(float runawaySpeed, float runawaySightDistance, float runawayDangerDistance) {
        super();
        this.runawaySpeed = runawaySpeed;
        runawaySightDistanceSqr = runawaySightDistance * runawaySightDistance;
        runawayDangerDistanceSqr = runawayDangerDistance * runawayDangerDistance;
        runawayTimer = new Timer();
    }

    public void update() {
        float dx = monster.getX() - human.getX();
        float dy = monster.getY() - human.getY();
        float dz = monster.getZ() - human.getZ();
        float distanceSqr = MathNumbers.magnitudeSqr(dx, dy, dz);

        runawayTimer.update();

        if (distanceSqr < runawaySightDistanceSqr || (distanceSqr < runawayDangerDistanceSqr && human.isDangerous())) {
            runawayTimer.reset(RUNAWAY_TIME);
            run(dx, dy);
        }

        if (runawayTimer.done()) {
            runawayTimer.reset(WANDER_TIME);
            if (MathRandom.random(WANDER_PROB))
                wander();
            else
                stand();
        }

        float deltaZ = flyHeight - monster.getZ();
        moveControl.dz = deltaZ > 0 ? deltaZ * FLY_SMOOTH_MULT : 0;

        // todo being damaged (even if from distance greater than runawayDangerDistanceSqr), activate runawayTimer
    }

    private void run(float dx, float dy) {
        moveControl.dx = dx;
        moveControl.dy = dy;
        moveControl.speed = runawaySpeed;
        moveControl.theta = (float) Math.atan2(dy, dx);
    }

    private void stand() {
        moveControl.dx = 0;
        moveControl.dy = 0;
        moveControl.speed = 0;
    }
}