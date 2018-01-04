package character;

import util.IntersectionFinder;
import world.World;

public class Monster extends Character {
    private static final float[] COLOR = new float[] {1, 0, 0};
    private static final float LIFE = 10, LIFE_REGEN = 0, SHIELD = 0, SHIELD_REGEN = 0;

    private Human human;
    private MoveControl moveControl;

    public Monster(float x, float y, float z, float theta, float thetaZ, IntersectionFinder intersectionFinder, Human human) {
        super(x, y, z, theta, thetaZ, intersectionFinder, LIFE, LIFE_REGEN, SHIELD, SHIELD_REGEN, COLOR);
        this.human = human;
        moveControl = new MoveControl();
    }

    @Override
    public void update(World world) {
        moveControl.dx = human.getX() - getX();
        moveControl.dy = human.getY() - getY();
        moveControl.theta = (float) Math.atan2(moveControl.dy, moveControl.dx);
        move(moveControl);
    }
}