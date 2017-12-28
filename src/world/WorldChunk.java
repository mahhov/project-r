package world;

import geometry.CoordinateI3;
import shape.CubeInstanced;

class WorldChunk {
    private int offsetX, offsetY, offsetZ;
    private CubeInstanced cubeInstanced;
    private int[][][] cubes;

    WorldChunk(CoordinateI3 coordinate) {
        cubeInstanced = new CubeInstanced(World.CHUNK_VOLUME);
        offsetX = coordinate.x * World.CHUNK_SIZE;
        offsetY = coordinate.y * World.CHUNK_SIZE;
        offsetZ = coordinate.z * World.CHUNK_SIZE;
        cubes = new int[World.CHUNK_SIZE][World.CHUNK_SIZE][World.CHUNK_SIZE];
    }

    void addCube(CoordinateI3 coordinate) {
        cubes[coordinate.x][coordinate.y][coordinate.z] = 1;
    }

    void doneAddingCubes() {
        for (int x = 0; x < World.CHUNK_SIZE; x++)
            for (int y = 0; y < World.CHUNK_SIZE; y++)
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                    checkAddCube(x, y, z);
        cubeInstanced.doneAdding();
    }

    private void checkAddCube(int x, int y, int z) {
        if (!hasCube(x, y, z))
            return;

        for (int xx = -1; xx <= 1; xx++)
            for (int yy = -1; yy <= 1; yy++)
                for (int zz = -1; zz <= 1; zz++) {
                    if (xx == 0 || yy == 0 || zz == 0)
                        continue;
                    if (!hasCube(x + xx, y + yy, z + zz)) {
                        cubeInstanced.add(x + .5f + offsetX, z + .5f + offsetZ, y + .5f + offsetY);
                        return;
                    }
                }
    }

    private boolean inBounds(int x, int y, int z) {
        return x >= 0 && y >= 0 && z >= 0 && x < World.CHUNK_SIZE && y < World.CHUNK_SIZE && z < World.CHUNK_SIZE;
    }

    private boolean hasCube(int x, int y, int z) {
        return inBounds(x, y, z) && cubes[x][y][z] != 0;
    }

    int draw() {
        cubeInstanced.draw();
        return 0;
    }
}