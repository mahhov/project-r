package util;

import geometry.Coordinate;

public class IntersectionFinder {
    // temp vars
    private float x, y, z;
    private float dx, dy, dz;
    private float halfSize, edgeDx, edgeDy, edgeDz;
    private int selectedDelta;
    private float edgeX, edgeY, edgeZ;
    private float deltaX, deltaY, deltaZ, delta;
    private int nextX, nextY, nextZ;
    private boolean collisionX, collisionY, grounded;

    private Map map;

    public IntersectionFinder(Map map) {
        this.map = map;
    }

    public Intersection find(float[] orig, float[] dir, float maxMove, float size) {
        if (dir[0] == 0 && dir[1] == 0 && dir[2] == 0)
            return null;

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
                        if (dy == 0 && dz == 0)
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
                        if (dx == 0 && dz == 0)
                            return createIntersection();
                        dy = 0;
                        edgeDy = 0;
                        maxMove = renormalizeDxyz(maxMove);
                    }

                } else if (selectedDelta == 2 && !moveableZ(x, y, nextZ)) {
                    grounded = dz < 0;
                    if (dx == 0 && dy == 0)
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

    private void prepare(float[] orig, float[] dir, float size) {
        x = orig[0];
        y = orig[1];
        z = orig[2];

        dir = MathNumbers.setMagnitude(dir[0], dir[1], dir[2], 1);
        dx = dir[0];
        dy = dir[1];
        dz = dir[2];

        halfSize = size / 2;
        edgeDx = dx == 0 ? 0 : (dx < 0 ? -halfSize : halfSize);
        edgeDy = dy == 0 ? 0 : (dy < 0 ? -halfSize : halfSize);
        edgeDz = dz == 0 ? 0 : (dz < 0 ? -halfSize : halfSize);

        collisionX = false;
        collisionY = false;
        grounded = false;
    }

    private float getMove(float pos, float dir) {
        if (dir > 0)
            return ((int) pos + 1 - pos) / dir;
        else if (dir < 0)
            return (pos - (int) pos) / -dir;
        else
            return 2;
    }

    private void chooseDelta() {
        if (deltaX < deltaY)
            if (deltaX < deltaZ) {
                selectedDelta = 0;
                delta = deltaX;
            } else {
                selectedDelta = 2;
                delta = deltaZ;
            }
        else if (deltaY < deltaZ) {
            selectedDelta = 1;
            delta = deltaY;
        } else {
            selectedDelta = 2;
            delta = deltaZ;
        }
    }

    private boolean moveableX(int x, float y, float z) {
        for (int yi = -1; yi <= 1; yi++)
            for (int zi = -1; zi <= 1; zi++)
                if (!map.moveable(x, (int) (y + yi * halfSize), (int) (z + zi * halfSize)))
                    return false;
        return true;
    }

    private int moveableXWithRise(int x, float y, float z) {
        outerloop:
        for (int zi = 1; zi <= 2; zi++) {
            for (int yi = -1; yi <= 1; yi++)
                if (!map.moveable(x, (int) (y + yi * halfSize), (int) (z + zi * halfSize)))
                    continue outerloop;
            return zi;
        }
        return 0;
    }

    private boolean moveableY(float x, int y, float z) {
        for (int xi = -1; xi <= 1; xi++)
            for (int zi = -1; zi <= 1; zi++)
                if (!map.moveable((int) (x + xi * halfSize), y, (int) (z + zi * halfSize)))
                    return false;
        return true;
    }

    private int moveableYWithRise(float x, int y, float z) {
        outerloop:
        for (int zi = 1; zi <= 2; zi++) {
            for (int xi = -1; xi <= 1; xi++)
                if (!map.moveable((int) (x + xi * halfSize), y, (int) (z + zi * halfSize)))
                    continue outerloop;
            return zi;
        }
        return 0;
    }

    private boolean moveableZ(float x, float y, int z) {
        for (int xi = -1; xi <= 1; xi++)
            for (int yi = -1; yi <= 1; yi++)
                if (!map.moveable((int) (x + xi * halfSize), (int) (y + yi * halfSize), z))
                    return false;
        return true;
    }

    private float renormalizeDxyz(float maxMove) {
        float magnitude = MathNumbers.magnitude(dx, dy, dz);
        float invMagnitude = 1 / magnitude;
        dx *= invMagnitude;
        dy *= invMagnitude;
        dz *= invMagnitude;
        return maxMove * magnitude;
    }

    private Intersection createIntersection() {
        return new Intersection(x, y, z, collisionX, collisionY, grounded);
    }

    private Intersection createIntersection(float x, float y, float z) {
        return new Intersection(x, y, z, collisionX, collisionY, grounded);
    }

    public class Intersection {
        public final Coordinate coordinate;
        public final boolean collisionX, collisionY, grounded;

        private Intersection(float x, float y, float z, boolean collisionX, boolean collisionY, boolean grounded) {
            coordinate = new Coordinate(x, y, z);
            this.collisionX = collisionX;
            this.collisionY = collisionY;
            this.grounded = grounded;
        }
    }
}