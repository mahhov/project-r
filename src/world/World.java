package world;

import geometry.CoordinateI3;
import util.MathNumbers;
import world.generator.WorldGenerator;

public class World {
    static final int CHUNK_SIZE = 16;
    private static final int DRAW_CHUNKS = 8;

    private int chunkWidth, chunkLength, chunkHeight;
    private WorldChunk[][][] chunks;

    public World(int width, int length, int height) { // todo multithread
        chunkWidth = (width - 1) / CHUNK_SIZE + 1;
        chunkLength = (length - 1) / CHUNK_SIZE + 1;
        chunkHeight = (height - 1) / CHUNK_SIZE + 1;
        chunks = new WorldChunk[chunkWidth][chunkLength][chunkHeight];
        System.out.println((chunkWidth * chunkLength * chunkHeight) + " chunks");

        int[][] heightMap = WorldGenerator.generate(width, length, height);
        int count = 0;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < length; y++) {
                count += heightMap[x][y];
                for (int z = 0; z < heightMap[x][y]; z++)
                    insertCube(new CoordinateI3(x, y, z));
            }

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

    public void draw(int x, int y, int z) {
        int centerX = x / CHUNK_SIZE;
        int centerY = y / CHUNK_SIZE;
        int centerZ = z / CHUNK_SIZE;
        int startX = MathNumbers.max(centerX - DRAW_CHUNKS, 0);
        int startY = MathNumbers.max(centerY - DRAW_CHUNKS, 0);
        int startZ = MathNumbers.max(centerZ - DRAW_CHUNKS, 0);
        int endX = MathNumbers.min(centerX + DRAW_CHUNKS, chunkWidth);
        int endY = MathNumbers.min(centerY + DRAW_CHUNKS, chunkLength);
        int endZ = MathNumbers.min(centerZ + DRAW_CHUNKS, chunkHeight);

        for (int chunkX = startX; chunkX < endX; chunkX++)
            for (int chunkY = startY; chunkY < endY; chunkY++)
                for (int chunkZ = startZ; chunkZ < endZ; chunkZ++)
                    if (chunks[chunkX][chunkY][chunkZ] != null)
                        chunks[chunkX][chunkY][chunkZ].draw();
    }
}