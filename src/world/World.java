package world;

import character.Human;
import character.Monster;
import engine.Engine;
import geometry.CoordinateI3;
import shape.CubeInstancedFaces;
import util.*;
import world.generator.WorldGenerator;

public class World implements Map {
    static final int CHUNK_SIZE = 128;
    private static final int DRAW_CHUNKS = 4;

    private int width, length, height;
    private int chunkWidth, chunkLength, chunkHeight;
    private WorldChunk[][][] chunks;
    private int[][] heightMap;
    private CoordinateI3 viewStart, viewEnd;

    private Human human;
    private LList<WorldElement> elements;
    private IntersectionFinder intersectionFinder;
    private CubeInstancedFaces dynamicCubeInstancedFaces;

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
        heightMap = WorldGenerator.generate(width, length, height / 3);
        elements = new LList<>();
        intersectionFinder = new IntersectionFinder(this);
        dynamicCubeInstancedFaces = new CubeInstancedFaces(Monster.COLOR);
        Timer.time("world creation");
    }

    public void addWorldElement(WorldElement element) {
        elements.addTail(element);
    }

    public void addRandomMonsters(int n) {
        for (int i = 0; i < n; i++)
            addWorldElement(new Monster(MathRandom.random(0, width), MathRandom.random(0, length), 8 * Engine.SCALE, 0, 0, intersectionFinder, human, dynamicCubeInstancedFaces));
    }

    public void setHuman(Human human) {
        addWorldElement(human);
        this.human = human;
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

        dynamicCubeInstancedFaces.reset();
        for (WorldElement element : elements)
            element.draw();
        dynamicCubeInstancedFaces.doneAdding();
        dynamicCubeInstancedFaces.draw();
    }

    private void generateChunks() {
        Timer.restart();
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
            Timer.time("Cubes " + WorldChunk.debugCubeCount);
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

    @Override
    public boolean moveable(int x, int y, int z) {
        return !hasCube(new CoordinateI3(x, y, z));
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public int height() {
        return height;
    }

    public IntersectionFinder getIntersectionFinder() {
        return intersectionFinder;
    }
}