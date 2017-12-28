package world;

import geometry.CoordinateI3;
import shape.Cube;

class WorldChunk {
    private int offsetX, offsetY, offsetZ;
    private Cube[][][] cubes;

    WorldChunk(CoordinateI3 coordinate) {
        offsetX = coordinate.x * World.CHUNK_SIZE;
        offsetY = coordinate.y * World.CHUNK_SIZE;
        offsetZ = coordinate.z * World.CHUNK_SIZE;
        cubes = new Cube[World.CHUNK_SIZE][World.CHUNK_SIZE][World.CHUNK_SIZE];
    }

    void insertCube(CoordinateI3 coordinate) {
        cubes[coordinate.x][coordinate.y][coordinate.z] = new Cube(coordinate.x + .5f + offsetX, coordinate.z + .5f + offsetZ, coordinate.y + .5f + offsetY);
    }

    private boolean inBounds(int x, int y, int z) {
        return x >= 0 && y >= 0 && z >= 0 && x < World.CHUNK_SIZE && y < World.CHUNK_SIZE && z < World.CHUNK_SIZE;
    }

    private boolean hasCube(int x, int y, int z) {
        return cubes[x][y][z] != null;
    }

    int draw() {
        int count = 0;
        for (int x = 0; x < World.CHUNK_SIZE; x++)
            for (int y = 0; y < World.CHUNK_SIZE; y++)
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                    count += checkDraw(x, y, z);
        return count;
    }

    private int checkDraw(int x, int y, int z) {
        if (!hasCube(x, y, z))
            return 0;

        for (int xx = -1; xx < 2; xx++)
            for (int yy = -1; yy < 2; yy++)
                for (int zz = -1; zz < 2; zz++)
                    if ((xx != 0 || yy != 0 || zz != 0) && (!inBounds(x + xx, y + yy, z + zz) || !hasCube(x + xx, y + yy, z + zz))) {
                        cubes[x][y][z].draw();
                        return 1;
                    }
        return 0;
    }
}
