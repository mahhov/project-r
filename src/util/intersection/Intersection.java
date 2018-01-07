package util.intersection;

import geometry.Coordinate;
import world.WorldElement;

public class Intersection {
    public final Coordinate coordinate;
    public final boolean collisionX, collisionY, grounded;
    public final WorldElement hitElement;

    Intersection(float x, float y, float z, boolean collisionX, boolean collisionY, boolean grounded, WorldElement hitElement) {
        coordinate = new Coordinate(x, y, z);
        this.collisionX = collisionX;
        this.collisionY = collisionY;
        this.grounded = grounded;
        this.hitElement = hitElement;
    }
}