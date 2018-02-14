package util.math;

public class MathArrays {
    public static float[] pluckArray3(float[] raw, int[] indicies) {
        float[] plucked = new float[indicies.length * 3];
        for (int i = 0; i < indicies.length; i++)
            System.arraycopy(raw, indicies[i] * 3, plucked, i * 3, 3);
        return plucked;
    }

    public static float[] repeatArray(float[] raw, int count) {
        float[] repeated = new float[raw.length * count];
        for (int i = 0; i < count; i++)
            System.arraycopy(raw, 0, repeated, i * raw.length, raw.length);
        return repeated;
    }
}