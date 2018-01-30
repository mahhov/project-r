package character.monster.motion;

import character.Human;
import character.Monster;
import util.MathAngles;
import util.MathNumbers;
import util.MathRandom;

public class RunawayMotion extends MonsterMotion {
    private static final int RUNAWAY_TIME = 300, WANDER_TIME = 100;
    private static final float WANDER_PROB = .7f;

    private float wanderSpeed, runawaySpeed;
    private float runawaySightDistanceSqr, runawayDangerDistanceSqr;
    private Timer runawayTimer;

    public RunawayMotion(Monster monster, Human human, float wanderSpeed, float runawaySpeed, float runawaySightDistance, float runawayDangerDistance) {
        super(monster, human);
        this.wanderSpeed = wanderSpeed;
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
    }

    private void run(float dx, float dy) {
        moveControl.dx = dx;
        moveControl.dy = dy;
        moveControl.speed = runawaySpeed;
        moveControl.theta = (float) Math.atan2(dy, dx);
    }

    private void wander() {
        float angle = MathRandom.random(0, MathAngles.PI * 2);
        moveControl.dx = MathAngles.cos(angle);
        moveControl.dy = MathAngles.sin(angle);
        moveControl.speed = wanderSpeed;
        moveControl.theta = (float) Math.atan2(moveControl.dy, moveControl.dx);
    }

    private void stand() {
        moveControl.dx = 0;
        moveControl.dy = 0;
        moveControl.speed = 0;
    }
}