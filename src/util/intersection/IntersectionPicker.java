package util.intersection;

import util.Map;
import util.MathNumbers;

public class IntersectionPicker extends Intersectioner {
    private Picker picker;

    public IntersectionPicker(Map map, Picker picker) {
        super(map);
        this.picker = picker;
    }

    public Intersection cameraPick() {
        float[] orig = new float[] {picker.getWorldX(), picker.getWorldY(), picker.getWorldZ()};
        float[] dir = new float[] {picker.getWorldDirX(), picker.getWorldDirY(), picker.getWorldDirZ()};

        if (MathNumbers.isAllZero(dir))
            return createZeroDirIntersection(orig);

        prepare(orig, dir, .05f); // todo make constant

        x += dx * picker.getPickOffset();
        y += dy * picker.getPickOffset();
        z += dz * picker.getPickOffset();

        while (true) {
            edgeX = x + edgeDx;
            edgeY = y + edgeDy;
            edgeZ = z + edgeDz;

            deltaX = getMove(edgeX, dx);
            deltaY = getMove(edgeY, dy);
            deltaZ = getMove(edgeZ, dz);

            chooseDelta();

            delta += MathNumbers.EPSILON;
            nextX = MathNumbers.intNeg(edgeX + dx * delta);
            nextY = MathNumbers.intNeg(edgeY + dy * delta);
            nextZ = MathNumbers.intNeg(edgeZ + dz * delta);

            if (selectedDelta == 0 && !moveableX(nextX, y, z))
                return createIntersection();

            else if (selectedDelta == 1 && !moveableY(x, nextY, z))
                return createIntersection();

            else if (selectedDelta == 2 && !moveableZ(x, y, nextZ))
                return createIntersection();

            else {
                x += dx * delta;
                y += dy * delta;
                z += dz * delta;

                if (hit(x, y, z, halfSize))
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