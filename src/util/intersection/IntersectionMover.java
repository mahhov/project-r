package util.intersection;

import util.MathNumbers;

public class IntersectionMover extends Intersectioner {
    public IntersectionMover(Map map) {
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
                return createIntersection(x + dx * maxMove, y + dy * maxMove, z + dz * maxMove);

            } else {
                delta += MathNumbers.EPSILON;
                setNextXYZ();

                if (selectedDelta == 0 && !movableX(nextX, y, z)) {
                    if (movableXWithRise(nextX, y, z))
                        z++;
                    else {
                        collisionX = true;
                        if (MathNumbers.isZero(dy) && MathNumbers.isZero(dz))
                            return createIntersection();
                        dx = 0;
                        edgeDx = 0;
                        maxMove = renormalizeDxyz(maxMove);
                    }

                } else if (selectedDelta == 1 && !movableY(x, nextY, z)) {
                    if (movableYWithRise(x, nextY, z))
                        z++;
                    else {
                        collisionY = true;
                        if (MathNumbers.isZero(dx) && MathNumbers.isZero(dz))
                            return createIntersection();
                        dy = 0;
                        edgeDy = 0;
                        maxMove = renormalizeDxyz(maxMove);
                    }

                } else if (selectedDelta == 2 && !movableZ(x, y, nextZ)) {
                    grounded = dz < 0;
                    if (MathNumbers.isZero(dx) && MathNumbers.isZero(dy))
                        return createIntersection();
                    dz = 0;
                    edgeDz = 0;
                    maxMove = renormalizeDxyz(maxMove);

                } else {
                    x += dx * delta;
                    y += dy * delta;
                    z += dz * delta;
                    maxMove -= delta;
                }
            }
        }
    }
}