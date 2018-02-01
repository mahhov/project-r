package character.monster.motion;

import util.MathNumbers;
import util.MathRandom;

public class RetaliateMotion extends MonsterMotion {
    private static final int RETALIATE_TIME = 300, WANDER_TIME = 100;
    private static final float WANDER_PROB = .7f;
    private static final float RETALIATE_DISTANCE_SPEED_MULT = .2f;
    private static final float RETALIATE_FORGET_DISTANCE_SQR = 100;

    private Timer timer;
    private boolean damageTaken;
    private boolean retaliate;

    public RetaliateMotion() {
        super();
        timer = new Timer();
    }

    public void update() {
        float dx = human.getX() - monster.getX();
        float dy = human.getY() - monster.getY();
        float dz = human.getZ() - monster.getZ();
        float distanceSqr = MathNumbers.magnitudeSqr(dx, dy, dz);

        timer.update();

        if (distanceSqr < hostilitySightDistanceSqr || (distanceSqr < hostilityDangerDistanceSqr && human.isDangerous()) || damageTaken) {
            timer.reset(RETALIATE_TIME);
            retaliate = true;
            damageTaken = false;
        }

        if (timer.done()) {
            timer.reset(WANDER_TIME);
            retaliate = false;
            if (MathRandom.random(WANDER_PROB))
                wander();
            else
                stand();

        } else if (retaliate) {
            float flatDistanceSqr = MathNumbers.magnitudeSqr(dx, dy);
            run(dx, dy, MathNumbers.min(hostilitySpeed, flatDistanceSqr * RETALIATE_DISTANCE_SPEED_MULT));
            if (flatDistanceSqr < RETALIATE_FORGET_DISTANCE_SQR)
                timer.reset(RETALIATE_TIME);
        }

        jet();
    }

    @Override
    public void damageTaken() {
        damageTaken = true;
    }
}