package world.particle;

import util.math.MathRandom;

public class JetParticle extends Particle {
    private static final float[] COLOR = new float[] {1, 1, 1, .5f};
    private static final float MAX_V = .005f, Z_V_SHIFT = MAX_V * 2 / 3;
    private static final float SPREAD = .25f;
    private static int TIME = 300;
    private static final float SIZE = .15f;

    public JetParticle(float x, float y, float z) {
        super(x + MathRandom.random(-SPREAD, SPREAD), y + MathRandom.random(-SPREAD, SPREAD), z + MathRandom.random(-SPREAD, SPREAD),
                MathRandom.random(-MAX_V, MAX_V), MathRandom.random(-MAX_V, MAX_V), MathRandom.random(-MAX_V, MAX_V) - Z_V_SHIFT, TIME, COLOR, SIZE);
    }
}