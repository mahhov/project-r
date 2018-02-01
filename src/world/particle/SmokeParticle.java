package world.particle;

import util.MathRandom;
import world.World;

public class SmokeParticle extends Particle {
    private static final float MAX_V = .01f, Z_V_SHIFT = MAX_V * 2 / 3;
    private static int TIME = 300;
    private static final float SIZE = .1f;

    public SmokeParticle(float x, float y, float z, float[] color) {
        super(x, y, z, MathRandom.random(-MAX_V, MAX_V), MathRandom.random(-MAX_V, MAX_V), MathRandom.random(-MAX_V, MAX_V) + Z_V_SHIFT, TIME, color, SIZE);
    }

    @Override
    public boolean update(World world) {
        vx *= .985f;
        vy *= .985f;
        vz *= .985f;
        vz += .00005f;
        return super.update(world);
    }
}