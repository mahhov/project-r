package world;

import geometry.CoordinateI3;
import shape.CubeInstancedFaces;

class WorldChunk {
    static int debugCubeCount;

    private int offsetX, offsetY, offsetZ;
    private CubeInstancedFaces cubeInstancedFaces;
    private int[][][] cubes;
    private boolean worldEmpty, drawEmpty;

    WorldChunk(CoordinateI3 coordinate) {
        cubeInstancedFaces = new CubeInstancedFaces();
        offsetX = coordinate.x * World.CHUNK_SIZE;
        offsetY = coordinate.y * World.CHUNK_SIZE;
        offsetZ = coordinate.z * World.CHUNK_SIZE;
        cubes = new int[World.CHUNK_SIZE][World.CHUNK_SIZE][World.CHUNK_SIZE];
        worldEmpty = true;
        drawEmpty = true;
    }

    void addCube(CoordinateI3 coordinate) {
        worldEmpty = false;
        cubes[coordinate.x][coordinate.y][coordinate.z] = 1;
    }

    void doneAddingCubes(World world) {
        boolean[] sides;
        for (int x = 0; x < World.CHUNK_SIZE; x++)
            for (int y = 0; y < World.CHUNK_SIZE; y++)
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                    if ((sides = checkAddCube(x, y, z, world)) != null) {
                        debugCubeCount++;
                        drawEmpty = false;
                        cubeInstancedFaces.add(x + .5f + offsetX, z + .5f + offsetZ, -(y + .5f + offsetY), sides);
                    }
        if (!drawEmpty)
            cubeInstancedFaces.doneAdding();
    }

    private static final int MAX = World.CHUNK_SIZE - 1;

    private boolean[] checkAddCube(int x, int y, int z, World world) {
        if (cubes[x][y][z] == 0)
            return null;

        boolean[] sides = new boolean[6];

        sides[CubeInstancedFaces.LEFT_SIDE] = !hasCube(x - 1, y, z, world);
        sides[CubeInstancedFaces.RIGHT_SIDE] = !hasCube(x + 1, y, z, world);
        sides[CubeInstancedFaces.FRONT_SIDE] = !hasCube(x, y - 1, z, world);
        sides[CubeInstancedFaces.BACK_SIDE] = !hasCube(x, y + 1, z, world);
        sides[CubeInstancedFaces.TOP_SIDE] = !hasCube(x, y, z + 1, world);
        sides[CubeInstancedFaces.BOTTOM_SIDE] = !hasCube(x, y, z - 1, world);

        for (int i = 0; i < 6; i++)
            if (sides[i])
                return sides;

        return null;
    }

    private boolean inBounds(int x, int y, int z) {
        return x >= 0 && y >= 0 && z >= 0 && x < World.CHUNK_SIZE && y < World.CHUNK_SIZE && z < World.CHUNK_SIZE;
    }

    boolean hasCube(int x, int y, int z, World world) {
        return inBounds(x, y, z) ? cubes[x][y][z] != 0 : world.hasCube(new CoordinateI3(x + offsetX, y + offsetY, z + offsetZ));
    }

    int getOffsetX() {
        return offsetX;
    }

    int getOffsetY() {
        return offsetY;
    }

    int getOffsetZ() {
        return offsetZ;
    }

    void draw() {
        if (!drawEmpty)
            cubeInstancedFaces.draw();
    }
}