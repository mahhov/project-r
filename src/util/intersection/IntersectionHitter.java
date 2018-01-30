package util.intersection;

import util.MathNumbers;

public class IntersectionHitter extends Intersectioner {
    public IntersectionHitter(Map map) {
        super(map);
    }

    public Intersection find(float[] orig, float[] dir, float maxMove, float size) {
        if (MathNumbers.isAllZero(dir))
            return createZeroDirIntersection(orig);

        prepare(orig, dir, size);

        while (true) {
            edgeX = x + edgeDx;
            edgeY = y + edgeDy;
            edgeZ = z + edgeDz;

            deltaX = getMove(edgeX, dx);
            deltaY = getMove(edgeY, dy);
            deltaZ = getMove(edgeZ, dz);

            chooseDelta();

            if (delta > maxMove) {
                maxMove = MathNumbers.max(maxMove - MathNumbers.EPSILON, 0);
                x += dx * maxMove;
                y += dy * maxMove;
                z += dz * maxMove;
                hitElement = hit(x, y, z, halfSize);
                return createIntersection();

            } else {
                delta += MathNumbers.EPSILON;
                setNextXYZ();

                if (selectedDelta == 0 && !movableX(nextX, y, z)) {
                    grounded = true;
                    return createIntersection();

                } else if (selectedDelta == 1 && !movableY(x, nextY, z)) {
                    grounded = true;
                    return createIntersection();

                } else if (selectedDelta == 2 && !movableZ(x, y, nextZ)) {
                    grounded = true;
                    return createIntersection();

                } else {
                    x += dx * delta;
                    y += dy * delta;
                    z += dz * delta;
                    maxMove -= delta;

                    hitElement = hit(x, y, z, halfSize);
                    if (hitElement != null) {
                        return createIntersection();
                    }
                }
            }
        }
    }
}