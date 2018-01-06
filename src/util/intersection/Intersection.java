package util.intersection;

import geometry.Coordinate;

public class Intersection {
    public final Coordinate coordinate;
    public final boolean collisionX, collisionY, grounded;

    Intersection(float x, float y, float z, boolean collisionX, boolean collisionY, boolean grounded) {
        coordinate = new Coordinate(x, y, z);
        this.collisionX = collisionX;
        this.collisionY = collisionY;
        this.grounded = grounded;
    }
}