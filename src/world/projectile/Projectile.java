package world.projectile;

import shape.CubeInstancedFaces;
import util.IntersectionFinder;
import util.MathNumbers;
import world.World;
import world.WorldElement;

public class Projectile implements WorldElement {
    private static final float SPEED = 5;
    private static final float AIR_FRICTION = 0.97f, GRAVITY = .02f; // todo share with character constatns

    private float x, y, z;
    private float vx, vy, vz;
    private boolean complete;

    private IntersectionFinder intersectionFinder;
    private CubeInstancedFaces cubeInstancedFaces;

    public Projectile(float x, float y, float z, float vx, float vy, float vz) {
        this.x = x;
        this.y = y;
        this.z = z;

        float v[] = MathNumbers.setMagnitude(vx, vy, vz, SPEED);
        this.vx = v[0];
        this.vy = v[1];
        this.vz = v[2];
    }

    public void connectWorld(IntersectionFinder intersectionFinder, CubeInstancedFaces cubeInstancedFaces) {
        this.intersectionFinder = intersectionFinder;
        this.cubeInstancedFaces = cubeInstancedFaces;
    }

    @Override
    public void update(World world) {
        if (complete)
            return;

        vx *= AIR_FRICTION;
        vy *= AIR_FRICTION;
        vz = (vz - GRAVITY) * AIR_FRICTION;

        IntersectionFinder.Intersection intersection = intersectionFinder.find(new float[] {x, y, z}, new float[] {vx, vy, vz}, MathNumbers.magnitude(vx, vy, vz), 1);
        x = intersection.coordinate.getX();
        y = intersection.coordinate.getY();
        z = intersection.coordinate.getZ();

        if (intersection.grounded || intersection.collisionX || intersection.collisionY) {
            complete = true;
            world.doDamage(x, y, z, 2, 10);
        }
    }

    @Override
    public boolean takeDamage(float amount) {
        return false;
    }

    @Override
    public void draw() {
        cubeInstancedFaces.add(x, z, -y, 0, 0);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }

    @Override
    public float getSize() {
        return 1;
    }
}