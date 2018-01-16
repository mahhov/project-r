package util;

import java.util.Random;

public class MathRandom {
    private static final Random R = new Random();

    // exclusive
    public static float random(float min, float max) {
        return R.nextFloat() * (max - min) + min;
    }

    // exclusive
    public static int random(int min, int max) {
        return R.nextInt(max - min) + min;
    }

    public static boolean random(double trueWeight) {
        return R.nextDouble() < trueWeight;
    }
}