package world;

import geometry.CoordinateI3;
import util.LList;
import util.MathNumbers;
import util.Timer;
import world.generator.WorldGenerator;

public class World {
    static final int CHUNK_SIZE = 128;
    private static final int DRAW_CHUNKS = 4;

    private int width, length, height;
    private int chunkWidth, chunkLength, chunkHeight;
    private WorldChunk[][][] chunks;
    private int[][] heightMap;
    private CoordinateI3 viewStart, viewEnd;

    private LList<WorldElement> elements;

    public World(int width, int length, int height) {
        Timer.restart();
        this.width = width;
        this.length = length;
        this.height = height;
        chunkWidth = (width - 1) / CHUNK_SIZE + 1;
        chunkLength = (length - 1) / CHUNK_SIZE + 1;
        chunkHeight = (height - 1) / CHUNK_SIZE + 1;
        chunks = new WorldChunk[chunkWidth][chunkLength][chunkHeight];
        System.out.println((chunkWidth * chunkLength * chunkHeight) + " chunks");
        heightMap = WorldGenerator.generate(width, length, height);
        elements = new LList<>();
        Timer.time("world creation");
    }

    public void addWorldElement(WorldElement element) {
        elements.addTail(element);
    }

    public void setCameraCoordinate(CoordinateI3 cameraCoordinate) {
        int centerX = cameraCoordinate.x / CHUNK_SIZE;
        int centerY = cameraCoordinate.y / CHUNK_SIZE;
        int centerZ = cameraCoordinate.z / CHUNK_SIZE;
        int startX = MathNumbers.max(centerX - DRAW_CHUNKS, 0);
        int startY = MathNumbers.max(centerY - DRAW_CHUNKS, 0);
        int startZ = MathNumbers.max(centerZ - DRAW_CHUNKS, 0);
        int endX = MathNumbers.min(centerX + DRAW_CHUNKS, chunkWidth);
        int endY = MathNumbers.min(centerY + DRAW_CHUNKS, chunkLength);
        int endZ = MathNumbers.min(centerZ + DRAW_CHUNKS, chunkHeight);
        viewStart = new CoordinateI3(startX, startY, startZ);
        viewEnd = new CoordinateI3(endX, endY, endZ);
    }

    public void update() {
        generateChunks();
        for (WorldElement element : elements)
            element.update(this);
    }

    private WorldChunk getChunk(CoordinateI3 chunkCoordinate) {
        return chunks[chunkCoordinate.x][chunkCoordinate.y][chunkCoordinate.z];
    }

    private boolean inBounds(CoordinateI3 coordinate) {
        return coordinate.x >= 0 && coordinate.y >= 0 && coordinate.z >= 0 && coordinate.x < width && coordinate.y < length && coordinate.z < height;
    }

    boolean hasCube(CoordinateI3 coordinate) {
        if (!inBounds(coordinate))
            return true;

        CoordinateI3 chunkCoordinate = coordinate.divide(CHUNK_SIZE);
        CoordinateI3 cubeCoordinate = coordinate.subtract(chunkCoordinate, CHUNK_SIZE);
        return getChunk(chunkCoordinate) != null && getChunk(chunkCoordinate).hasCube(cubeCoordinate.x, cubeCoordinate.y, cubeCoordinate.z, this);
    }

    public void draw() {
        for (int chunkX = viewStart.x; chunkX < viewEnd.x; chunkX++)
            for (int chunkY = viewStart.y; chunkY < viewEnd.y; chunkY++)
                for (int chunkZ = viewStart.z; chunkZ < viewEnd.z; chunkZ++)
                    if (chunks[chunkX][chunkY][chunkZ] != null)
                        chunks[chunkX][chunkY][chunkZ].draw();

        for (WorldElement element : elements)
            element.draw();
    }

    private void generateChunks() {
        LList<WorldChunk> generatedChunks = new LList<>();

        for (int chunkX = viewStart.x; chunkX < viewEnd.x; chunkX++)
            for (int chunkY = viewStart.y; chunkY < viewEnd.y; chunkY++)
                for (int chunkZ = viewStart.z; chunkZ < viewEnd.z; chunkZ++) {
                    CoordinateI3 coordinate = new CoordinateI3(chunkX, chunkY, chunkZ);
                    if (getChunk(coordinate) == null) {
                        chunks[chunkX][chunkY][chunkZ] = createChunk(coordinate);
                        generatedChunks.addTail(chunks[chunkX][chunkY][chunkZ]);
                    }
                }

        for (WorldChunk chunk : generatedChunks)
            chunk.doneAddingCubes(this);

        if (generatedChunks.size() > 0)
            System.out.println("Cubes " + WorldChunk.debugCubeCount);
    }

    private WorldChunk createChunk(CoordinateI3 coordinate) {
        WorldChunk chunk = new WorldChunk(coordinate);
        int maxX = MathNumbers.min(CHUNK_SIZE, width - chunk.getOffsetX());
        int maxY = MathNumbers.min(CHUNK_SIZE, length - chunk.getOffsetY());
        for (int x = 0; x < maxX; x++)
            for (int y = 0; y < maxY; y++) {
                int height = MathNumbers.min(heightMap[x + chunk.getOffsetX()][y + chunk.getOffsetY()] - chunk.getOffsetZ(), CHUNK_SIZE);
                for (int z = 0; z < height; z++)
                    chunk.addCube(new CoordinateI3(x, y, z));
            }
        return chunk;
    }
}