package world;

import engine.Engine;
import geometry.CoordinateI3;
import util.LList;
import util.Timer;
import util.math.MathRandom;
import world.worldmap.SimplexHeightWorldMapGenerator;
import world.worldmap.WorldMapGenerator;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class WorldGenerator {
    private static final int THREAD_COUNT = 4;

    private ExecutorService generatorExecutors;
    private WorldMapGenerator worldMapGenerator;
    private World world;

    WorldGenerator(World world, int height) {
        generatorExecutors = Executors.newFixedThreadPool(THREAD_COUNT);
        worldMapGenerator = new SimplexHeightWorldMapGenerator(World.CHUNK_SIZE, World.CHUNK_SIZE, World.CHUNK_SIZE, height);
        this.world = world;
    }

    void generateChunks(CoordinateI3 start, CoordinateI3 end) {
        Timer.restart(0);
        LList<WorldChunkGenerator> generators = new LList<>();

        for (int chunkX = start.x; chunkX < end.x; chunkX++)
            for (int chunkY = start.y; chunkY < end.y; chunkY++)
                for (int chunkZ = start.z; chunkZ < end.z; chunkZ++) {
                    CoordinateI3 coordinate = new CoordinateI3(chunkX, chunkY, chunkZ);
                    if (world.getChunk(coordinate) == null)
                        generators.addTail(new WorldChunkGenerator(generatorExecutors, coordinate, worldMapGenerator));
                }

        for (WorldChunkGenerator generator : generators)
            try {
                CoordinateI3 coordinate = generator.getCoordinate();
                world.setChunk(coordinate, generator.getFuture().get());
                generator.complete();
                populateChunk(coordinate);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        if (generators.size() > 0)
            Timer.time(0, "Chunk creation " + generators.size());
    }

    private void populateChunk(CoordinateI3 coordinate) {
        for (int i = 0; i < 3; i++) {
            int x = coordinate.x * World.CHUNK_SIZE + MathRandom.random(0, World.CHUNK_SIZE);
            int y = coordinate.y * World.CHUNK_SIZE + MathRandom.random(0, World.CHUNK_SIZE);
            int z = 8 * Engine.SCALE_Z;
            //            addWorldElement(new Monster(x, y, z, 0, 0, intersectionMover, human, dynamicCubeInstancedFaces, MonsterGenerator.createRandomDetails()));
        }
    }

    void shutDownGeneratorExecutors() {
        generatorExecutors.shutdown();
    }
}