package geometry;

public class Coordinate {
    private float x, y, z;

    public Coordinate() {
    }

    public Coordinate(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void add(Coordinate coordinate) {
        x += coordinate.x;
        y += coordinate.y;
        z += coordinate.z;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}