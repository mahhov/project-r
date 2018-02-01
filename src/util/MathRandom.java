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
        return max > min ? R.nextInt(max - min) + min : min;
    }

    public static boolean random(double trueWeight) {
        return R.nextDouble() < trueWeight;
    }

    public static String randomString() {
        return "random string " + random(0, 100);
    }

    public static float[] randomColor() {
        return new float[] {random(0, 1f), random(0, 1f), random(0, 1f)};
    }
}