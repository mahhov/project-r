package util;

import util.math.MathRandom;

public class Distribution {
    private int min, max;

    public Distribution(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int get() {
        return MathRandom.random(min, max);
    }
}