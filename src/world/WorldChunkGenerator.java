package world;

import geometry.CoordinateI3;
import shape.CubeInstancedFaces;
import world.worldmap.WorldMapGenerator;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

class WorldChunkGenerator implements Callable<WorldChunk> {
    private World world;
    private CubeInstancedFaces cubeInstancedFaces;
    private CoordinateI3 coordinate;
    private WorldMapGenerator generator;

    private Future<WorldChunk> future;
    private WorldChunk worldChunk;

    WorldChunkGenerator(ExecutorService executor, World world, CoordinateI3 coordinate, WorldMapGenerator generator) {
        this.world = world;
        this.cubeInstancedFaces = new CubeInstancedFaces();
        this.coordinate = coordinate;
        this.generator = generator;
        future = executor.submit(this);
    }

    void complete() {
        worldChunk.doneFilling();
    }

    CoordinateI3 getCoordinate() {
        return coordinate;
    }

    Future<WorldChunk> getFuture() {
        return future;
    }

    @Override
    public WorldChunk call() throws Exception {
        worldChunk = world.createChunk(coordinate);
        worldChunk.generate(cubeInstancedFaces, generator);
        return worldChunk;
    }
}