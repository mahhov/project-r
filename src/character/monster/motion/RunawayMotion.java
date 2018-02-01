package character.monster.motion;

import util.MathNumbers;
import util.MathRandom;

public class RunawayMotion extends MonsterMotion {
    private static final int RUNAWAY_TIME = 300, WANDER_TIME = 100;
    private static final float WANDER_PROB = .7f;

    private Timer runawayTimer;
    private boolean damageTaken;

    public RunawayMotion() {
        super();
        runawayTimer = new Timer();
    }

    public void update() {
        float dx = monster.getX() - human.getX();
        float dy = monster.getY() - human.getY();
        float dz = monster.getZ() - human.getZ();
        float distanceSqr = MathNumbers.magnitudeSqr(dx, dy, dz);

        runawayTimer.update();

        if (distanceSqr < hostilitySightDistanceSqr || (distanceSqr < hostilityDangerDistanceSqr && human.isDangerous()) || damageTaken) {
            runawayTimer.reset(RUNAWAY_TIME);
            run(dx, dy, hostilitySpeed);
            damageTaken = false;
        }

        if (runawayTimer.done()) {
            runawayTimer.reset(WANDER_TIME);
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