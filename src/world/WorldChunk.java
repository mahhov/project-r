package world;

import geometry.CoordinateI3;

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

    void draw() {
        for (int x = 0; x < World.CHUNK_SIZE; x++)
            for (int y = 0; y < World.CHUNK_SIZE; y++)
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                    if (cubes[x][y][z] != null)
                        cubes[x][y][z].draw();
    }
}
