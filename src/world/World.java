package world;

import engine.Engine;
import geometry.CoordinateI3;
import util.MathRandom;

public class World {
    static final int CHUNK_SIZE = 16, CHUNK_VOLUME = CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE;
    private int chunkWidth, chunkLength, chunkHeight;
    private WorldChunk[][][] chunks;

    public World(int width, int length, int height) { // todo multithread & extract generation
        chunkWidth = (width - 1) / CHUNK_SIZE + 1;
        chunkLength = (length - 1) / CHUNK_SIZE + 1;
        chunkHeight = (height - 1) / CHUNK_SIZE + 1;
        chunks = new WorldChunk[chunkWidth][chunkLength][chunkHeight];
        System.out.println((chunkWidth * chunkLength * chunkHeight) + " chunks");

        int count = 0;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < length; y++) {
                int h = MathRandom.random(1, height);
                count += h;
                for (int z = 0; z < h; z++)
                    insertCube(new CoordinateI3(x, y, z));
            }

        // todo try having only 1 cube inseted, and a bunch of null chunks

        for (int x = 0; x < chunkWidth; x++)
            for (int y = 0; y < chunkLength; y++)
                for (int z = 0; z < chunkHeight; z++)
                    if (chunks[x][y][z] != null)
                        chunks[x][y][z].doneAddingCubes();

        System.out.println("cube count: " + count);
    }

    public void update() {
    }

    private void insertCube(CoordinateI3 coordinate) {
        CoordinateI3 chunkCoordinate = coordinate.divide(CHUNK_SIZE);
        CoordinateI3 cubeCoordinate = coordinate.subtract(chunkCoordinate, CHUNK_SIZE);

        if (getChunk(chunkCoordinate) == null)
            setChunk(chunkCoordinate, new WorldChunk(chunkCoordinate));

        getChunk(chunkCoordinate).addCube(cubeCoordinate);
    }

    private WorldChunk getChunk(CoordinateI3 chunkCoordinate) {
        return chunks[chunkCoordinate.x][chunkCoordinate.y][chunkCoordinate.z];
    }

    private void setChunk(CoordinateI3 chunkCoordinate, WorldChunk chunk) {
        chunks[chunkCoordinate.x][chunkCoordinate.y][chunkCoordinate.z] = chunk;
    }

    public void draw() {
        int count = 0;
        for (int x = 0; x < chunkWidth; x++)
            for (int y = 0; y < chunkLength; y++)
                for (int z = 0; z < chunkHeight; z++)
                    if (chunks[x][y][z] != null)
                        count += chunks[x][y][z].draw();

        if (Engine.DEBUG_PRINT_DRAW_COUNT)
            System.out.println("drew " + count);
    }
}