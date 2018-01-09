package world;

import geometry.CoordinateI3;
import shape.CubeInstancedFaces;
import util.LList;
import util.Timer;
import world.generator.SimplexHeightMapWorldGenerator;

class WorldChunk {
    private CubeInstancedFaces cubeInstancedFaces;
    private int offsetX, offsetY, offsetZ;
    private byte[][][] map;
    private boolean worldEmpty, drawEmpty;
    private DynamicCell dynamicCells;

    WorldChunk(CubeInstancedFaces cubeInstancedFaces, CoordinateI3 coordinate, SimplexHeightMapWorldGenerator generator) {
        Timer.restart(1);
        this.cubeInstancedFaces = cubeInstancedFaces;
        offsetX = coordinate.x * World.CHUNK_SIZE;
        offsetY = coordinate.y * World.CHUNK_SIZE;
        offsetZ = coordinate.z * World.CHUNK_SIZE;
        worldEmpty = true;
        drawEmpty = true;

        dynamicCells = new DynamicCell();
        Timer.time(1);

        Timer.restart(2);
        map = generator.generate(offsetX, offsetY, offsetZ);
        Timer.time(2);
        Timer.restart(3);
        fill();
        Timer.time(3);
    }

    private void fill() {
        boolean[] sides;

        for (int x = 0; x < World.CHUNK_SIZE; x++)
            for (int y = 0; y < World.CHUNK_SIZE; y++)
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                    if (map[x + 1][y + 1][z + 1] == 1) {
                        worldEmpty = false;
                        if ((sides = checkAddCube(x, y, z)) != null) {
                            drawEmpty = false;
                            cubeInstancedFaces.add(x + .5f + offsetX, z + .5f + offsetZ, -(y + .5f + offsetY), sides);
                        }
                    }
    }

    void doneFilling() {
        if (!drawEmpty)
            cubeInstancedFaces.doneAdding();
    }

    private boolean[] checkAddCube(int x, int y, int z) {
        boolean[] sides = new boolean[6];

        sides[CubeInstancedFaces.LEFT_SIDE] = !hasCube(new CoordinateI3(x - 1, y, z));
        sides[CubeInstancedFaces.RIGHT_SIDE] = !hasCube(new CoordinateI3(x + 1, y, z));
        sides[CubeInstancedFaces.FRONT_SIDE] = !hasCube(new CoordinateI3(x, y - 1, z));
        sides[CubeInstancedFaces.BACK_SIDE] = !hasCube(new CoordinateI3(x, y + 1, z));
        sides[CubeInstancedFaces.TOP_SIDE] = !hasCube(new CoordinateI3(x, y, z + 1));
        sides[CubeInstancedFaces.BOTTOM_SIDE] = !hasCube(new CoordinateI3(x, y, z - 1));

        for (int i = 0; i < 6; i++)
            if (sides[i])
                return sides;

        return null;
    }

    boolean hasCube(CoordinateI3 cubeCoordinate) {
        return map[cubeCoordinate.x + 1][cubeCoordinate.y + 1][cubeCoordinate.z + 1] != 0;
    }

    LList<WorldElement>.Node addDynamicElement(WorldElement element) {
        return dynamicCells.add(element);
    }

    void removeDynamicElement(LList<WorldElement>.Node elementNode) {
        dynamicCells.remove(elementNode);
    }

    WorldElement checkDynamicElement(float preciseX, float preciseY, float preciseZ, float range) {
        if (dynamicCells.empty())
            return null;
        return dynamicCells.checkHit(preciseX, preciseY, preciseZ, range);
    }

    void draw() {
        if (!drawEmpty)
            cubeInstancedFaces.draw();
    }
}