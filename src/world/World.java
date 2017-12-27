package world;

import geometry.CoordinateI3;
import util.MathRandom;

public class World {
    static final int CHUNK_SIZE = 16;
    private int chunkWidth, chunkLength, chunkHeight;
    private WorldChunk[][][] chunks;

    public World(int width, int length, int height) {
        chunkWidth = (width - 1) / CHUNK_SIZE + 1;
        chunkLength = (length - 1) / CHUNK_SIZE + 1;
        chunkHeight = (height - 1) / CHUNK_SIZE + 1;
        chunks = new WorldChunk[chunkWidth][chunkLength][chunkHeight];

        for (int x = 0; x < width; x++)
            for (int y = 0; y < length; y++) {
                int h = MathRandom.random(1, height);
                for (int z = 0; z < h; z++)
                    insertCube(new CoordinateI3(x, y, z));
            }
    }

    public void update() {
    }

    private void insertCube(CoordinateI3 coordinate) {
        CoordinateI3 chunkCoordinate = coordinate.divide(CHUNK_SIZE);
        CoordinateI3 cubeCoordinate = coordinate.subtract(chunkCoordinate, CHUNK_SIZE);

        if (getChunk(chunkCoordinate) == null)
            setChunk(chunkCoordinate, new WorldChunk(chunkCoordinate));

        getChunk(chunkCoordinate).insertCube(cubeCoordinate);
    }

    private WorldChunk getChunk(CoordinateI3 chunkCoordinate) {
        return chunks[chunkCoordinate.x][chunkCoordinate.y][chunkCoordinate.z];
    }

    private void setChunk(CoordinateI3 chunkCoordinate, WorldChunk chunk) {
        chunks[chunkCoordinate.x][chunkCoordinate.y][chunkCoordinate.z] = chunk;
    }

    public void draw() {
        for (int x = 0; x < chunkWidth; x++)
            for (int y = 0; y < chunkLength; y++)
                for (int z = 0; z < chunkHeight; z++)
                    if (chunks[x][y][z] != null)
                        chunks[x][y][z].draw();
    }
}