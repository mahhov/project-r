package util.intersection;

import util.math.MathNumbers;
import world.WorldElement;

class Intersectioner {
    // temp vars
    float x, y, z;
    float dx, dy, dz;
    float halfSize, edgeDx, edgeDy, edgeDz;
    int selectedDelta;
    float edgeX, edgeY, edgeZ;
    float deltaX, deltaY, deltaZ, delta;
    int nextX, nextY, nextZ;
    boolean collisionX, collisionY, grounded;
    WorldElement hitElement;

    private Map map;

    Intersectioner(Map map) {
        this.map = map;
    }

    void prepare(float[] orig, float[] dir, float size) {
        x = orig[0];
        y = orig[1];
        z = orig[2];

        dir = MathNumbers.setMagnitude(dir[0], dir[1], dir[2], 1);
        dx = MathNumbers.zero(dir[0]);
        dy = MathNumbers.zero(dir[1]);
        dz = MathNumbers.zero(dir[2]);

        halfSize = size / 2;
        edgeDx = MathNumbers.isZero(dx) ? 0 : (dx < 0 ? -halfSize : halfSize);
        edgeDy = MathNumbers.isZero(dy) ? 0 : (dy < 0 ? -halfSize : halfSize);
        edgeDz = MathNumbers.isZero(dz) ? 0 : (dz < 0 ? -halfSize : halfSize);

        collisionX = false;
        collisionY = false;
        grounded = false;
    }

    float getMove(float pos, float dir) {
        if (dir > 0)
            return ((int) pos + 1 - pos) / dir;
        else if (dir < 0) {
            return (pos - (int) pos) / -dir;
        } else
            return 2;
    }

    void chooseDelta() {
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

    void setNextXYZ(float x, float y, float z) {
        if (selectedDelta == 0) {
            nextX = MathNumbers.intNeg(x) + (dx < 0 ? -1 : 1);
            nextY = MathNumbers.intNeg(y);
            nextZ = MathNumbers.intNeg(z);
        } else if (selectedDelta == 1) {
            nextX = MathNumbers.intNeg(x);
            nextY = MathNumbers.intNeg(y) + (dy < 0 ? -1 : 1);
            nextZ = MathNumbers.intNeg(z);
        } else {
            nextX = MathNumbers.intNeg(x);
            nextY = MathNumbers.intNeg(y);
            nextZ = MathNumbers.intNeg(z) + (dz < 0 ? -1 : 1);
        }
    }

    boolean movable(int x, int y, int z) {
        return map.movable(x, y, z);
    }

    boolean movableX(int x, float y, float z) {
        for (int yi = -1; yi <= 1; yi++)
            for (int zi = -1; zi <= 1; zi++)
                if (!map.movable(x, (int) (y + yi * halfSize), (int) (z + zi * halfSize)))
                    return false;
        return true;
    }

    boolean movableXWithRise(int x, float y, float z) {
        for (int yi = -1; yi <= 1; yi++)
            if (!map.movable(x, (int) (y + yi * halfSize), (int) (z + 1)))
                return false;
        return true;
    }

    boolean movableY(float x, int y, float z) {
        for (int xi = -1; xi <= 1; xi++)
            for (int zi = -1; zi <= 1; zi++)
                if (!map.movable((int) (x + xi * halfSize), y, (int) (z + zi * halfSize)))
                    return false;
        return true;
    }

    boolean movableYWithRise(float x, int y, float z) {
        for (int xi = -1; xi <= 1; xi++)
            if (!map.movable((int) (x + xi * halfSize), y, (int) (z + 1)))
                return false;
        return true;
    }

    boolean movableZ(float x, float y, int z) {
        for (int xi = -1; xi <= 1; xi++)
            for (int yi = -1; yi <= 1; yi++)
                if (!map.movable((int) (x + xi * halfSize), (int) (y + yi * halfSize), z))
                    return false;
        return true;
    }

    WorldElement hit(float x, float y, float z, float range) {
        return map.hit(x, y, z, range);
    }

    float renormalizeDxyz(float maxMove) {
        float magnitude = MathNumbers.magnitude(dx, dy, dz);
        float invMagnitude = 1 / magnitude;
        dx *= invMagnitude;
        dy *= invMagnitude;
        dz *= invMagnitude;
        return maxMove * magnitude;
    }

    float edgeify(int nextPos, float dir) {
        if (dir < 0)
            return nextPos + 1 + halfSize;
        else
            return nextPos - halfSize - MathNumbers.EPSILON;
    }

    Intersection createIntersection() {
        return new Intersection(x, y, z, collisionX, collisionY, grounded, hitElement);
    }

    Intersection createZeroDirIntersection(float orig[]) {
        return new Intersection(orig[0], orig[1], orig[2], false, false, false, null);
    }

    Intersection createIntersection(float x, float y, float z) {
        return new Intersection(x, y, z, collisionX, collisionY, grounded, hitElement);
    }
}