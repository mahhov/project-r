package util.intersection;

import util.MathNumbers;

public class IntersectionPicker extends Intersectioner {
    private static final float RANGE = .5f;
    
    private Picker picker;

    public IntersectionPicker(Map map, Picker picker) {
        super(map);
        this.picker = picker;
    }

    public Intersection find() {
        float[] orig = new float[] {picker.getWorldX(), picker.getWorldY(), picker.getWorldZ()};
        float[] dir = new float[] {picker.getWorldDirX(), picker.getWorldDirY(), picker.getWorldDirZ()};

        if (MathNumbers.isAllZero(dir))
            return createZeroDirIntersection(orig);

        prepare(orig, dir, 0);

        x += dx * picker.getPickOffset();
        y += dy * picker.getPickOffset();
        z += dz * picker.getPickOffset();

        while (true) {
            deltaX = getMove(x, dx);
            deltaY = getMove(y, dy);
            deltaZ = getMove(z, dz);

            chooseDelta();

            delta += MathNumbers.EPSILON;
            setNextXYZ(x, y, z);

            if (!movable(nextX, nextY, nextZ))
                return createIntersection();

            else {
                x += dx * delta;
                y += dy * delta;
                z += dz * delta;

                hitElement = hit(x, y, z, RANGE);
                if (hitElement != null)
                    return createIntersection();
            }
        }
    }

    public interface Picker {
        float getWorldX();

        float getWorldY();

        float getWorldZ();

        float getWorldDirX();

        float getWorldDirY();

        float getWorldDirZ();

        float getPickOffset();
    }
}