package geometry;

public class CoordinateI3 {
    public final int x, y, z;

    public CoordinateI3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CoordinateI3 add(int addend) {
        return new CoordinateI3(x + addend, y + addend, z + addend);
    }

    public CoordinateI3 add(CoordinateI3 addend) {
        return new CoordinateI3(x + addend.x, y + addend.y, z + addend.z);
    }

    public CoordinateI3 add(CoordinateI3 addend, int addendScale) {
        return new CoordinateI3(x + addend.x * addendScale, y + addend.y * addendScale, z + addend.z * addendScale);
    }

    public CoordinateI3 subtract(int subtrahend) {
        return new CoordinateI3(x - subtrahend, y - subtrahend, z - subtrahend);
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

    public boolean equals(CoordinateI3 coordinate) {
        return x == coordinate.x && y == coordinate.y && z == coordinate.z;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}