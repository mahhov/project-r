package world.particle;

import util.MathRandom;
import world.World;

public class SmokeParticle extends Particle {
    private static final float[] COLOR = new float[] {1, 1, 1, .5f};
    private static final float MAX_V = .01f, Z_V_SHIFT = MAX_V * 2 / 3;
    private static final float SPREAD = .25f;
    private static int TIME = 300;
    private static final float SIZE = .1f;
    
    private static final float FRICTION = .99f, RISE = .00005f;

    public SmokeParticle(float x, float y, float z) {
        super(x + MathRandom.random(-SPREAD, SPREAD), y + MathRandom.random(-SPREAD, SPREAD), z + MathRandom.random(-SPREAD, SPREAD),
                MathRandom.random(-MAX_V, MAX_V), MathRandom.random(-MAX_V, MAX_V), MathRandom.random(-MAX_V, MAX_V) + Z_V_SHIFT, TIME, COLOR, SIZE);
    }

    @Override
    public boolean update(World world) {
        vx *= FRICTION;
        vy *= FRICTION;
        vz *= FRICTION;
        vz += RISE;
        return super.update(world);
    }
}