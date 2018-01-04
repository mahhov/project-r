package util;

public class MathNumbers {
    public static final float EPSILON = 0.0001f;
    public static final float LOG2 = (float) Math.log(2);

    public static float max(float v1, float v2) {
        return v1 > v2 ? v1 : v2;
    }

    public static int max(int v1, int v2) {
        return v1 > v2 ? v1 : v2;
    }

    public static float min(float v1, float v2) {
        return v1 < v2 ? v1 : v2;
    }

    public static float min(float v1, float v2, float v3) {
        return v1 < v2 ? (v1 < v3 ? v1 : v3) : (v2 < v3 ? v2 : v3);
    }

    public static int min(int v1, int v2) {
        return v1 < v2 ? v1 : v2;
    }

    public static float maxMin(float v, float max, float min) {
        return v > max ? max : (v < min ? min : v);
    }

    public static float magnitude(float dx, float dy) {
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public static float magnitude(float dx, float dy, float dz) {
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public static float[] setMagnitude(float x, float y, float z, float magnitude) {
        float currentMagnitude = magnitude(x, y, z);
        if (isZero(currentMagnitude))
            return new float[] {0, 0, 0};
        float mult = magnitude / magnitude(x, y, z);
        return new float[] {x * mult, y * mult, z * mult};
    }

    public static float magnitudeSqr(float dx, float dy, float dz) {
        return dx * dx + dy * dy + dz * dz;
    }

    public static boolean isZero(float value) {
        return value < EPSILON && value > -EPSILON;
    }

    public static int intNeg(float value) {
        return value < 0 ? (int) value - 1 : (int) value;
    }

    public static float log2(float x) {
        return (float) Math.log(x) / LOG2;
    }

    public static int powerOf2(int num) {
        return (int) Math.pow(2, (int) (log2(num) - EPSILON) + 1);
    }
}