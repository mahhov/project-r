package world.particle;

import util.math.MathRandom;

public class TrailParticle extends Particle {
    private static final float[] COLOR = new float[] {0, 0, 1, .5f};
    private static final float MAX_V = .003f;
    private static int TIME = 300;
    private static final float SIZE = .1f;

    public TrailParticle(float x, float y, float z) {
        super(x, y, z, MathRandom.random(-MAX_V, MAX_V), MathRandom.random(-MAX_V, MAX_V), MathRandom.random(-MAX_V, MAX_V), TIME, COLOR, SIZE);
    }
}