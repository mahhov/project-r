package world;

import character.Monster;
import character.monster.MonsterGenerator;
import engine.Engine;
import geometry.CoordinateI3;
import util.LList;
import util.Timer;
import util.math.MathRandom;
import world.element.ElementBox;
import world.worldmap.SimplexHeightWorldMapGenerator;
import world.worldmap.WorldMap;
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
                    if (!world.isChunkGenerated(coordinate))
                        generators.addTail(new WorldChunkGenerator(generatorExecutors, world, coordinate, worldMapGenerator));
                }

        for (WorldChunkGenerator generator : generators)
            try {
                CoordinateI3 coordinate = generator.getCoordinate();
                WorldChunk chunk = generator.getFuture().get();
                generator.complete();
                populateChunk(chunk, coordinate);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        if (generators.size() > 0)
            Timer.time(0, "Chunk creation " + generators.size());
    }

    private void populateChunk(WorldChunk chunk, CoordinateI3 coordinate) {
        WorldMap worldMap = chunk.getWorldMap();

        for (int i = 0; i < 3; i++) {
            int x = coordinate.x * World.CHUNK_SIZE + MathRandom.random(0, World.CHUNK_SIZE);
            int y = coordinate.y * World.CHUNK_SIZE + MathRandom.random(0, World.CHUNK_SIZE);
            int z = 8 * Engine.SCALE_Z;
            world.addMonster(new Monster(x, y, z, 0, 0, MonsterGenerator.createRandomDetails()));
        }

        for (int x = 0; x < World.CHUNK_SIZE; x++)
            for (int y = 0; y < World.CHUNK_SIZE; y++) {
                WorldMap.Terrain terrain = worldMap.getTerrain(x, y);
                int height = worldMap.heightMap[x + 1][y + 1] + 1;
                int z = height - chunk.offsetZ;

                if (z >= 0 && z < World.CHUNK_SIZE && terrain == WorldMap.Terrain.GREEN && MathRandom.random(.03)) {
                    int worldX = coordinate.x * World.CHUNK_SIZE + x;
                    int worldY = coordinate.y * World.CHUNK_SIZE + y;
                    world.addElement(new ElementBox(worldX + .5f, worldY + .5f, height + .5f));
                }
            }
    }

    void shutDownGeneratorExecutors() {
        generatorExecutors.shutdown();
    }
}