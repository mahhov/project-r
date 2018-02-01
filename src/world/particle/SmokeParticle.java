package world.particle;

import util.MathRandom;

public class SmokeParticle extends Particle {
    private static final float MAX_V = .01f;
    private static int TIME = 300;
    private static final float SIZE = .2f;

    public SmokeParticle(float x, float y, float z, float[] color) {
        super(x, y, z, MathRandom.random(-MAX_V, MAX_V), MathRandom.random(-MAX_V, MAX_V), MathRandom.random(-MAX_V, MAX_V), TIME, color, SIZE);
    }
}