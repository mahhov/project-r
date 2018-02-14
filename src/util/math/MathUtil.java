package util.math;

public class MathUtil {
    public static float[] colorMult(float[] color, float mult) {
        return new float[] {color[0] * mult, color[1] * mult, color[2] * mult, color[3]};
    }
}