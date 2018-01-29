package character.monster;

import character.Human;
import character.Monster;
import character.MoveControl;
import util.MathNumbers;

public class RunawayOnSightMonsterMotion {
    final Monster monster;
    final Human human;
    public final MoveControl moveControl;
    private float wanderSpeed, runawaySpeed; // todo wander mode initially and after running for set distance
    private float runawayBeginDistanceSqr;


    public RunawayOnSightMonsterMotion(Monster monster, Human human, float wanderSpeed, float runawaySpeed, float runawayBeginDistance) {
        this.monster = monster;
        this.human = human;
        this.moveControl = new MoveControl();
        this.wanderSpeed = wanderSpeed;
        this.runawaySpeed = runawaySpeed;
        this.runawayBeginDistanceSqr = runawayBeginDistance * runawayBeginDistance;
    }

    public void update() {
        float dx = monster.getX() - human.getX();
        float dy = monster.getY() - human.getY();
        float dz = monster.getZ() - human.getZ();
        float distanceSqr = MathNumbers.magnitudeSqr(dx, dy, dz);

        if (distanceSqr < runawayBeginDistanceSqr) {
            moveControl.dx = dx;
            moveControl.dy = dy;
            moveControl.speed = runawaySpeed;
            moveControl.theta = (float) Math.atan2(dy, dx);
        }
    }
}