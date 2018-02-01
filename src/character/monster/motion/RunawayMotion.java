package character.monster.motion;

import util.MathNumbers;
import util.MathRandom;

public class RunawayMotion extends MonsterMotion {
    private static final int RUNAWAY_TIME = 300, WANDER_TIME = 100;
    private static final float WANDER_PROB = .7f;

    private Timer timer;
    private boolean damageTaken;

    public RunawayMotion() {
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
            timer.reset(RUNAWAY_TIME);
            run(-dx, -dy, hostilitySpeed);
            damageTaken = false;
        }

        if (timer.done()) {
            timer.reset(MathRandom.random(WANDER_TIME / 2, WANDER_TIME));
            if (MathRandom.random(WANDER_PROB))
                wander();
            else
                stand();
        }

        jet();
    }

    @Override
    public void damageTaken() {
        damageTaken = true;
    }
}