package character.monster.motion;

import util.MathNumbers;
import util.MathRandom;

public class RetaliateMotion extends MonsterMotion {
    private static final int WANDER_TIME = 100;
    private static final float WANDER_PROB = .7f;
    private static final float RETALIATE_DISTANCE_SPEED_MULT = .2f;

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
            retaliate = true;
            damageTaken = false;
        }

        if (retaliate) {
            float flatDistanceSqr = MathNumbers.magnitudeSqr(dx, dy);
            run(dx, dy, MathNumbers.min(hostilitySpeed, flatDistanceSqr * RETALIATE_DISTANCE_SPEED_MULT));

        } else if (timer.done()) {
            timer.reset(WANDER_TIME);
            if (MathRandom.random(WANDER_PROB))
                wander();
            else
                stand();
        }

        jet();
    }
    // todo stop retaliation after time with no damaged human nor monster

    @Override
    public void damageTaken() {
        damageTaken = true;
    }
}