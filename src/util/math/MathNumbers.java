package util.math;

public class MathNumbers {
    public static final float EPSILON = 0.001f, BIG_EPSILON = .01f;
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

    public static int min(int v1, int v2, int v3) {
        return v1 < v2 ? (v1 < v3 ? v1 : v3) : (v2 < v3 ? v2 : v3);
    }

    public static float minMax(float v, float min, float max) {
        return v > max ? max : (v < min ? min : v);
    }

    public static int minMax(int v, int min, int max) {
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
        float mult = magnitude / currentMagnitude;
        return new float[] {x * mult, y * mult, z * mult};
    }

    public static float[] setMagnitude(float x, float y, float magnitude) {
        float currentMagnitude = magnitude(x, y);
        if (isZero(currentMagnitude))
            return new float[] {0, 0, 0};
        float mult = magnitude / currentMagnitude;
        return new float[] {x * mult, y * mult};
    }

    public static float magnitudeSqr(float dx, float dy) {
        return dx * dx + dy * dy;
    }

    public static float magnitudeSqr(float dx, float dy, float dz) {
        return dx * dx + dy * dy + dz * dz;
    }

    public static boolean isZero(float value) {
        return value < EPSILON && value > -EPSILON;
    }

    public static float zero(float value) {
        return isZero(value) ? 0 : value;
    }

    public static boolean isAllZero(float values[]) {
        for (float value : values)
            if (!isZero(value))
                return false;
        return false;
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