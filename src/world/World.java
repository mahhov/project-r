package world;

import character.Human;
import character.Monster;
import character.monster.MonsterGenerator;
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
import world.generator.SimplexHeightMapWorldGenerator;
import world.generator.WorldGenerator;
import world.projectile.Projectile;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class World implements Map {
    static final int CHUNK_SIZE = 128;
    private static final int DRAW_CHUNKS = 3;
    private static final int THREAD_COUNT = 4;

    private ExecutorService generatorExecutors;

    private int width, length, height;
    private int chunkWidth, chunkLength, chunkHeight;
    private WorldChunk[][][] chunks;
    private WorldGenerator worldGenerator;
    private CoordinateI3 viewStart, viewEnd;

    private Human human;
    private LList<WorldElement> elements;
    private IntersectionMover intersectionMover;
    private IntersectionPicker intersectionPicker;
    private IntersectionHitter intersectionHitter;
    private CubeInstancedFaces dynamicCubeInstancedFaces;

    private float largestElementSize;

    public World(int width, int length, int height, IntersectionPicker.Picker picker) {
        Timer.restart(0);
        generatorExecutors = Executors.newFixedThreadPool(THREAD_COUNT);

        this.width = width;
        this.length = length;
        this.height = height;
        chunkWidth = (width - 1) / CHUNK_SIZE + 1;
        chunkLength = (length - 1) / CHUNK_SIZE + 1;
        chunkHeight = (height - 1) / CHUNK_SIZE + 1;
        chunks = new WorldChunk[chunkWidth][chunkLength][chunkHeight];
        System.out.println("chunks: [" + chunkWidth + " x " + chunkLength + " x " + chunkHeight + "] total " + chunkWidth * chunkLength * chunkHeight);
        worldGenerator = new SimplexHeightMapWorldGenerator(CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, height / 2);

        elements = new LList<>();
        intersectionMover = new IntersectionMover(this);
        intersectionPicker = new IntersectionPicker(this, picker);
        intersectionHitter = new IntersectionHitter(this);
        dynamicCubeInstancedFaces = new CubeInstancedFaces(Monster.COLOR);
        Timer.time(0, "world creation");
    }

    private void addWorldElement(WorldElement element) {
        elements.addTail(element);
        largestElementSize = MathNumbers.max(largestElementSize, element.getSize());
    }

    public void setHuman(Human human) {
        addWorldElement(human);
        this.human = human;
    }

    public void addProjectile(Projectile projectile) {
        projectile.connectWorld(intersectionHitter, dynamicCubeInstancedFaces);
        addWorldElement(projectile);
    }

    public LList<WorldElement>.Node addDynamicElement(CoordinateI3 coordinate, WorldElement element) {
        CoordinateI3 chunkCoordinate = coordinate.divide(CHUNK_SIZE); // todo find replicates of these 3 lines and extract
        if (getChunk(chunkCoordinate) == null)
            return null;
        return getChunk(chunkCoordinate).addDynamicElement(element);
    }

    public void removeDynamicElement(CoordinateI3 coordinate, LList<WorldElement>.Node elementNode) {
        CoordinateI3 chunkCoordinate = coordinate.divide(CHUNK_SIZE);
        getChunk(chunkCoordinate).removeDynamicElement(elementNode);
    }

    public LList<WorldElement>.Node moveDynamicElement(CoordinateI3 coordinateFrom, CoordinateI3 coordinateTo, LList<WorldElement>.Node elementNode) {
        CoordinateI3 chunkCoordinateFrom = coordinateFrom.divide(CHUNK_SIZE);
        CoordinateI3 chunkCoordinateTo = coordinateTo.divide(CHUNK_SIZE);

        if (chunkCoordinateFrom.equals(chunkCoordinateTo))
            return elementNode;

        getChunk(chunkCoordinateFrom).removeDynamicElement(elementNode);
        return getChunk(chunkCoordinateTo).addDynamicElement(elementNode.getValue());
    }

    public void setCameraCoordinate(CoordinateI3 cameraCoordinate) {
        int centerX = cameraCoordinate.x / CHUNK_SIZE;
        int centerY = cameraCoordinate.y / CHUNK_SIZE;
        int centerZ = cameraCoordinate.z / CHUNK_SIZE;
        int startX = MathNumbers.max(centerX - DRAW_CHUNKS, 0);
        int startY = MathNumbers.max(centerY - DRAW_CHUNKS, 0);
        int startZ = MathNumbers.max(centerZ - DRAW_CHUNKS, 0);
        int endX = MathNumbers.min(centerX + DRAW_CHUNKS + 1, chunkWidth);
        int endY = MathNumbers.min(centerY + DRAW_CHUNKS + 1, chunkLength);
        int endZ = MathNumbers.min(centerZ + DRAW_CHUNKS + 1, chunkHeight);
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

    private boolean hasCube(CoordinateI3 coordinate) {
        CoordinateI3 chunkCoordinate = coordinate.divide(CHUNK_SIZE);
        CoordinateI3 cubeCoordinate = coordinate.subtract(chunkCoordinate, CHUNK_SIZE);
        return getChunk(chunkCoordinate) != null && getChunk(chunkCoordinate).hasCube(cubeCoordinate);
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
        Timer.restart(0);
        LList<WorldChunkGenerator> generators = new LList<>();

        for (int chunkX = viewStart.x; chunkX < viewEnd.x; chunkX++)
            for (int chunkY = viewStart.y; chunkY < viewEnd.y; chunkY++)
                for (int chunkZ = viewStart.z; chunkZ < viewEnd.z; chunkZ++) {
                    CoordinateI3 coordinate = new CoordinateI3(chunkX, chunkY, chunkZ);
                    if (getChunk(coordinate) == null)
                        generators.addTail(new WorldChunkGenerator(generatorExecutors, new CubeInstancedFaces(), coordinate, worldGenerator));
                }

        for (WorldChunkGenerator generator : generators)
            try {
                CoordinateI3 coordinate = generator.getCoordinate();
                chunks[coordinate.x][coordinate.y][coordinate.z] = generator.getFuture().get();
                generator.complete();
                populateChunk(coordinate);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        if (generators.size() > 0)
            Timer.time(0, "Chunk creation " + generators.size());
    }

    private void populateChunk(CoordinateI3 coordinate) {
        for (int i = 0; i < 10; i++) {
            int x = coordinate.x * CHUNK_SIZE + MathRandom.random(0, CHUNK_SIZE);
            int y = coordinate.y * CHUNK_SIZE + MathRandom.random(0, CHUNK_SIZE);
            int z = 8 * Engine.SCALE_Z;
            addWorldElement(new Monster(x, y, z, 0, 0, intersectionMover, human, dynamicCubeInstancedFaces, MonsterGenerator.createWormDetails()));
        }
    }

    public void shutDownGeneratorExecutors() {
        generatorExecutors.shutdown();
    }

    @Override
    public boolean movable(int x, int y, int z) {
        CoordinateI3 coordinate = new CoordinateI3(x, y, z);
        return inBounds(coordinate) && !hasCube(coordinate);
    }

    @Override
    public WorldElement hit(float x, float y, float z, float range) {
        int intX = (int) x;
        int intY = (int) y;
        int intZ = (int) z;
        int searchRange = (int) (range + largestElementSize / 2) + 1;

        int minX = MathNumbers.max(intX - searchRange, 0);
        int maxX = MathNumbers.min(intX + searchRange, width - 1);
        int minY = MathNumbers.max(intY - searchRange, 0);
        int maxY = MathNumbers.min(intY + searchRange, length - 1);
        int minZ = MathNumbers.max(intZ - searchRange, 0);
        int maxZ = MathNumbers.min(intZ + searchRange, height - 1);

        minX /= CHUNK_SIZE;
        minY /= CHUNK_SIZE;
        minZ /= CHUNK_SIZE;
        maxX /= CHUNK_SIZE;
        maxY /= CHUNK_SIZE;
        maxZ /= CHUNK_SIZE;

        WorldChunk chunk;
        WorldElement hit;

        for (int xi = minX; xi <= maxX; xi++)
            for (int yi = minY; yi <= maxY; yi++)
                for (int zi = minZ; zi <= maxZ; zi++)
                    if ((chunk = chunks[xi][yi][zi]) != null)
                        if ((hit = chunk.checkDynamicElement(x, y, z, range)) != null)
                            return hit;

        return null;
    }

    public IntersectionMover getIntersectionMover() {
        return intersectionMover;
    }

    public IntersectionPicker getIntersectionPicker() {
        return intersectionPicker;
    }
}