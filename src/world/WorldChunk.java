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
        cubeInstanced.add(coordinate.x + .5f + offsetX, coordinate.z + .5f + offsetZ, coordinate.y + .5f + offsetY);
    }

    void doneAddingCubes() {
        cubeInstanced.doneAdding();
    }

    private boolean inBounds(int x, int y, int z) {
        return x >= 0 && y >= 0 && z >= 0 && x < World.CHUNK_SIZE && y < World.CHUNK_SIZE && z < World.CHUNK_SIZE;
    }

    private boolean hasCube(int x, int y, int z) {
        return cubes[x][y][z] != 0;
    }

    int draw() {
        cubeInstanced.draw();
        return 0;
    }
}