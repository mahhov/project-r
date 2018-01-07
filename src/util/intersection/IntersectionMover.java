package util.intersection;

import util.Map;
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

            if (delta > maxMove)
                return createIntersection(x + dx * maxMove, y + dy * maxMove, z + dz * maxMove);

            else {
                delta += MathNumbers.EPSILON;
                nextX = MathNumbers.intNeg(edgeX + dx * delta);
                nextY = MathNumbers.intNeg(edgeY + dy * delta);
                nextZ = MathNumbers.intNeg(edgeZ + dz * delta);

                if (selectedDelta == 0 && !moveableX(nextX, y, z)) {
                    int rise = moveableXWithRise(nextX, y, z);
                    if (rise > 0)
                        z++;
                    else {
                        collisionX = true;
                        if (MathNumbers.isZero(dy) && MathNumbers.isZero(dz))
                            return createIntersection();
                        dx = 0;
                        edgeDx = 0;
                        maxMove = renormalizeDxyz(maxMove);
                    }

                } else if (selectedDelta == 1 && !moveableY(x, nextY, z)) {
                    int rise = moveableYWithRise(x, nextY, z);
                    if (rise > 0)
                        z++;
                    else {
                        collisionY = true;
                        if (MathNumbers.isZero(dx) && MathNumbers.isZero(dz))
                            return createIntersection();
                        dy = 0;
                        edgeDy = 0;
                        maxMove = renormalizeDxyz(maxMove);
                    }

                } else if (selectedDelta == 2 && !moveableZ(x, y, nextZ)) {
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