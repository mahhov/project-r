package util;

public class MathAngles {
    public static final float PI = (float) Math.PI, PI2 = PI * 2;

    private static float sinTable[];
    private static final int TRIG_ACCURACY = 500;

    static {
        Timer.restart(0);
        sinTable = new float[TRIG_ACCURACY];
        for (int i = 0; i < TRIG_ACCURACY; i++)
            sinTable[i] = (float) Math.sin(PI / 2 * i / TRIG_ACCURACY);

        Timer.time(0,"Math Angles Init");
    }

    public static float sin(float xd) {
        float x = xd / PI * 2;

        int sign;
        if (x < 0) {
            sign = -1;
            x = -x;
        } else
            sign = 1;

        x = x - ((int) (x / 4)) * 4;
        if (x >= 2) {
            sign *= -1;
            x -= 2;
        }

        if (x > 1)
            x = 2 - x;

        if (x == 1)
            return sign;

        return sign * sinTable[(int) (x * TRIG_ACCURACY)];
    }

    public static float cos(float x) {
        return sin(x + PI / 2);
    }

    public static float tan(float x) {
        return sin(x) / cos(x);
    }

    public static float toRadians(float degree) {
        return degree * PI / 180;
    }
}