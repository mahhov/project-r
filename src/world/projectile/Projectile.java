package world.projectile;

import shape.CubeInstancedFaces;
import util.MathNumbers;
import util.intersection.Intersection;
import util.intersection.IntersectionHitter;
import world.World;
import world.WorldElement;

public class Projectile implements WorldElement {
    private static final float SIZE = .3f, AREA = 3f, SPEED = 3f, DAMAGE = 10;
    private static final float AIR_FRICTION = 1f, GRAVITY = 0f;

    private float x, y, z;
    private float vx, vy, vz;

    private IntersectionHitter intersectionHitter;
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

    public void connectWorld(IntersectionHitter intersectionHitter, CubeInstancedFaces cubeInstancedFaces) {
        this.intersectionHitter = intersectionHitter;
        this.cubeInstancedFaces = cubeInstancedFaces;
    }

    @Override
    public boolean update(World world) {
        vx *= AIR_FRICTION;
        vy *= AIR_FRICTION;
        vz = (vz - GRAVITY) * AIR_FRICTION;

        Intersection intersection = intersectionHitter.find(new float[] {x, y, z}, new float[] {vx, vy, vz}, MathNumbers.magnitude(vx, vy, vz), SIZE);

        x = intersection.coordinate.getX();
        y = intersection.coordinate.getY();
        z = intersection.coordinate.getZ();

        if (intersection.grounded) {
            world.doDamage(x, y, z, AREA, DAMAGE);
            return true;
        }

        return false;
    }

    @Override
    public void takeDamage(float amount) {
    }

    @Override
    public void draw() {
        cubeInstancedFaces.add(x, z, -y, 0, 0, SIZE);
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