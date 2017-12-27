package geometry;

public class CoordinateI3 {
    public int x, y, z;

    public CoordinateI3() {
    }

    public CoordinateI3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CoordinateI3 subtract(CoordinateI3 subtrahend) {
        return new CoordinateI3(x - subtrahend.x, y - subtrahend.y, z - subtrahend.z);
    }

    public CoordinateI3 subtract(CoordinateI3 subtrahend, int subtrahendScale) {
        return new CoordinateI3(x - subtrahend.x * subtrahendScale, y - subtrahend.y * subtrahendScale, z - subtrahend.z * subtrahendScale);
    }

    public CoordinateI3 divide(int divisor) {
        return new CoordinateI3(x / divisor, y / divisor, z / divisor);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}