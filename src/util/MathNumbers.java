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
    
    public static int min(int v1, int v2) {
        return v1 < v2 ? v1 : v2;
    }

    public static float log2(float x) {
        return (float) Math.log(x) / LOG2;
    }

    public static int powerOf2(int num) {
        return (int) Math.pow(2, (int) (log2(num) - EPSILON) + 1);
    }
}