package world;

import character.Human;
import character.Monster;
import engine.Engine;
import geometry.CoordinateI3;
import shape.CubeInstancedFaces;
import util.LList;
import util.MathNumbers;
import util.MathRandom;
import util.Timer;
import util.intersection.IntersectionHitter;
import util.intersection.IntersectionMover;
import util.intersection.IntersectionPicker;
import util.intersection.Map;
import world.generator.HeightMapWorldGenerator;
import world.projectile.Projectile;

public class World implements Map {
    static final int CHUNK_SIZE = 128;
    private static final int DRAW_CHUNKS = 4;

    private int width, length, height;
    private int chunkWidth, chunkLength, chunkHeight;
    private WorldChunk[][][] chunks;
    private int generatedMap[][][], generatedMapHeight;
    private CoordinateI3 viewStart, viewEnd;

    private Human human;
    private LList<WorldElement> elements;
    private IntersectionMover intersectionMover;
    private IntersectionPicker intersectionPicker;
    private IntersectionHitter intersectionHitter;
    private CubeInstancedFaces dynamicCubeInstancedFaces;

    private float largestElementSize;

    public World(int width, int length, int height, IntersectionPicker.Picker picker) {
        Timer.restart();
        this.width = width;
        this.length = length;
        this.height = height;
        chunkWidth = (width - 1) / CHUNK_SIZE + 1;
        chunkLength = (length - 1) / CHUNK_SIZE + 1;
        chunkHeight = (height - 1) / CHUNK_SIZE + 1;
        chunks = new WorldChunk[chunkWidth][chunkLength][chunkHeight];
        System.out.println((chunkWidth * chunkLength * chunkHeight) + " chunks");
        generatedMapHeight = height / 10;
        generatedMap = HeightMapWorldGenerator.generate(width, length, generatedMapHeight);
        elements = new LList<>();
        intersectionMover = new IntersectionMover(this);
        intersectionPicker = new IntersectionPicker(this, picker);
        intersectionHitter = new IntersectionHitter(this);
        dynamicCubeInstancedFaces = new CubeInstancedFaces(Monster.COLOR);
        Timer.time("world creation");
    }

    private void addWorldElement(WorldElement element) {
        elements.addTail(element);
        largestElementSize = MathNumbers.max(largestElementSize, element.getSize());
    }

    public void setHuman(Human human) {
        addWorldElement(human);
        this.human = human;
    }

    public void addRandomMonsters(int n) {
        for (int i = 0; i < n; i++)
            addWorldElement(new Monster(MathRandom.random(0, width), MathRandom.random(0, length), 8 * Engine.SCALE, 0, 0, intersectionMover, human, dynamicCubeInstancedFaces));
    }

    public void addProjectile(Projectile projectile) {
        projectile.connectWorld(intersectionHitter, dynamicCubeInstancedFaces);
        addWorldElement(projectile);
    }

    public LList<WorldElement>.Node addDynamicElement(CoordinateI3 coordinate, WorldElement element) {
        CoordinateI3 chunkCoordinate = coordinate.divide(CHUNK_SIZE); // todo find replicates of these 3 lines and extract
        CoordinateI3 cubeCoordinate = coordinate.subtract(chunkCoordinate, CHUNK_SIZE);
        if (getChunk(chunkCoordinate) == null)
            return null;
        return getChunk(chunkCoordinate).addDynamicElement(cubeCoordinate, element);
    }

    public void removeDynamicElement(CoordinateI3 coordinate, LList<WorldElement>.Node elementNode) {
        CoordinateI3 chunkCoordinate = coordinate.divide(CHUNK_SIZE);
        CoordinateI3 cubeCoordinate = coordinate.subtract(chunkCoordinate, CHUNK_SIZE);
        getChunk(chunkCoordinate).removeDynamicElement(cubeCoordinate, elementNode);
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

        for (LList<WorldElement>.Node elementNode : elements.nodeIterator())
            if (elementNode.getValue().update(this))
                elements.remove(elementNode);
    }

    public void doDamage(float x, float y, float z, float range, float amount) {
        WorldElement hit = hit(x, y, z, range);
        if (hit != null)
            hit.takeDamage(amount);
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
        int maxZ = MathNumbers.min(CHUNK_SIZE, generatedMapHeight - chunk.getOffsetZ());
        for (int x = 0; x < maxX; x++)
            for (int y = 0; y < maxY; y++)
                for (int z = 0; z < maxZ; z++)
                    if (generatedMap[x + chunk.getOffsetX()][y + chunk.getOffsetY()][z + chunk.getOffsetZ()] == 1)
                        chunk.addCube(new CoordinateI3(x, y, z));
        return chunk;
    }

    @Override
    public boolean moveable(int x, int y, int z) {
        return !hasCube(new CoordinateI3(x, y, z));
    }

    @Override
    public WorldElement hit(float x, float y, float z, float range) {
        int intX = (int) x;
        int intY = (int) y;
        int intZ = (int) z;
        int searchRange = (int) (range + largestElementSize / 2) + 1;

        for (int xi = intX - searchRange; xi <= intX + searchRange; xi++)
            for (int yi = intY - searchRange; yi <= intY + searchRange; yi++)
                for (int zi = intZ - searchRange; zi <= intZ + searchRange; zi++) {
                    CoordinateI3 coordinate = new CoordinateI3(xi, yi, zi);
                    if (inBounds(coordinate)) {
                        CoordinateI3 chunkCoordinate = coordinate.divide(CHUNK_SIZE);
                        if (getChunk(chunkCoordinate) != null) {
                            CoordinateI3 cubeCoordinate = coordinate.subtract(chunkCoordinate, CHUNK_SIZE);
                            WorldElement hit = getChunk(chunkCoordinate).checkDynamicElement(cubeCoordinate.x, cubeCoordinate.y, cubeCoordinate.z, x, y, z, range);
                            if (hit != null)
                                return hit;
                        }
                    }
                }

        return null;
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

    public IntersectionMover getIntersectionMover() {
        return intersectionMover;
    }

    public IntersectionPicker getIntersectionPicker() {
        return intersectionPicker;
    }
}