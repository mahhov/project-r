package character.monster;

import character.Human;
import character.Monster;
import character.MoveControl;
import character.monster.attack.MonsterAttack;
import character.monster.motion.MonsterMotion;
import character.monster.motion.Timer;
import util.MathNumbers;
import util.MathRandom;

public class MonsterBehavior {
    enum State {PASSIVE, CURIOUS, HOSTILE, FRIGHTENED}

    private static final int RUNAWAY_TIME = 300, WANDER_TIME = 100;
    private static final float WANDER_PROB = .7f;

    private Monster monster;
    private Human human;
    private MonsterMotion motion;
    private MonsterAttack attack;

    private Timer timer;
    private State state;
    private float hostilitySightDistanceSqr, hostilityDangerDistanceSqr;
    private boolean damageTaken;

    MonsterBehavior(Monster monster, Human human, MonsterMotion motion, MonsterAttack attack, float hostilitySightDistanceSqr, float hostilityDangerDistanceSqr) {
        this.monster = monster;
        this.human = human;
        this.motion = motion;
        this.attack = attack;
        timer = new Timer();
        state = State.PASSIVE;
        this.hostilitySightDistanceSqr = hostilitySightDistanceSqr;
        this.hostilityDangerDistanceSqr = hostilityDangerDistanceSqr;
    }

    public void update() {
        float dx = human.getX() - monster.getX();
        float dy = human.getY() - monster.getY();
        float dz = human.getZ() - monster.getZ();
        float distanceSqr = MathNumbers.magnitudeSqr(dx, dy, dz);

        timer.update();

        if (distanceSqr < hostilitySightDistanceSqr || (distanceSqr < hostilityDangerDistanceSqr && human.isDangerous()) || damageTaken) {
            timer.reset(RUNAWAY_TIME);
            motion.run(-dx, -dy);
            damageTaken = false;
        }

        if (timer.done()) {
            timer.reset(MathRandom.random(WANDER_TIME / 2, WANDER_TIME));
            if (MathRandom.random(WANDER_PROB))
                motion.wander();
            else
                motion.stand();
        }

        motion.jet();

        if (state == State.HOSTILE)
            attack.update();
    }

    public void damageTaken() {
        damageTaken = true;
    }

    public MoveControl getMoveControl() {
        return motion.moveControl;
    }

    public void draw() {
        if (state == State.HOSTILE)
            attack.draw();
    }
}