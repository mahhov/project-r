package util;

public class MathRandom {
    // exclusive
    public static float random(float min, float max) {
        return (float) Math.random() * (max - min) + min;
    }

    // exclusive
    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }
}
