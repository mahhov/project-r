package world;

import geometry.CoordinateI3;
import shape.CubeInstancedFaces;
import util.LList;
import world.worldmap.WorldMapGenerator;
import world.worldmap.WorldMap;

class WorldChunk {
    private CubeInstancedFaces cubeInstancedFaces;
    private int offsetX, offsetY, offsetZ;
    private WorldMap worldMap;
    private boolean drawEmpty;
    private DynamicCell dynamicCells;

    WorldChunk(CubeInstancedFaces cubeInstancedFaces, CoordinateI3 coordinate, WorldMapGenerator generator) {
        this.cubeInstancedFaces = cubeInstancedFaces;
        offsetX = coordinate.x * World.CHUNK_SIZE;
        offsetY = coordinate.y * World.CHUNK_SIZE;
        offsetZ = coordinate.z * World.CHUNK_SIZE;
        drawEmpty = true;

        dynamicCells = new DynamicCell();

        worldMap = generator.generate(offsetX, offsetY, offsetZ);
        fill();
    }

    private void fill() {
        //        final boolean[] true6 = new boolean[] {true, true, true, true, true, true};
        //        final float[] white = new float[] {1, 1, 1, 1};

        boolean[] sides;

        for (int x = 0; x < World.CHUNK_SIZE; x++)
            for (int y = 0; y < World.CHUNK_SIZE; y++) {
                WorldMap.Terrain terrain = worldMap.getTerrain(x, y);
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                    if (worldMap.map[x + 1][y + 1][z + 1] == 1)
                        if ((sides = checkAddCube(x, y, z)) != null) {
                            drawEmpty = false;
                            cubeInstancedFaces.add(x + .5f + offsetX, z + .5f + offsetZ, -(y + .5f + offsetY), sides, terrain.color);
                        }

                //                int height = worldMap.heightMap[x + 1][y + 1] + 1;
                //                int z = height - offsetZ;
                //
                //                if (terrain == WorldMap.Terrain.GREEN && MathRandom.random(.1f)) {
                //                    if (z >= 0 && z < World.CHUNK_SIZE)
                //                        cubeInstancedFaces.add(x + .5f + offsetX, height + .5f, -(y + .5f + offsetY), true6, white);
                //                    if (z >= -1 && z < World.CHUNK_SIZE + 1)
                //                        worldMap.map[x + 1][y + 1][z + 1] = 1;
                //                }
            }
    }

    void doneFilling() {
        if (!drawEmpty)
            cubeInstancedFaces.doneAdding();
    }

    private boolean[] checkAddCube(int x, int y, int z) {
        boolean[] sides = new boolean[6];

        sides[CubeInstancedFaces.LEFT_SIDE] = !hasCube(x - 1, y, z);
        sides[CubeInstancedFaces.RIGHT_SIDE] = !hasCube(x + 1, y, z);
        sides[CubeInstancedFaces.FRONT_SIDE] = !hasCube(x, y - 1, z);
        sides[CubeInstancedFaces.BACK_SIDE] = !hasCube(x, y + 1, z);
        sides[CubeInstancedFaces.TOP_SIDE] = !hasCube(x, y, z + 1);
        sides[CubeInstancedFaces.BOTTOM_SIDE] = !hasCube(x, y, z - 1);

        for (int i = 0; i < 6; i++)
            if (sides[i])
                return sides;

        return null;
    }

    private boolean hasCube(int x, int y, int z) {
        return worldMap.map[x + 1][y + 1][z + 1] != 0;
    }

    boolean hasCube(CoordinateI3 cubeCoordinate) {
        return worldMap.map[cubeCoordinate.x + 1][cubeCoordinate.y + 1][cubeCoordinate.z + 1] != 0;
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