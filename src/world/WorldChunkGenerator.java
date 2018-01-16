package world;

import geometry.CoordinateI3;
import shape.CubeInstancedFaces;
import world.generator.WorldGenerator;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

class WorldChunkGenerator implements Callable<WorldChunk> {
    private CubeInstancedFaces cubeInstancedFaces;
    private CoordinateI3 coordinate;
    private WorldGenerator generator;

    private Future<WorldChunk> future;
    private WorldChunk worldChunk;

    WorldChunkGenerator(ExecutorService executor, CubeInstancedFaces cubeInstancedFaces, CoordinateI3 coordinate, WorldGenerator generator) {
        this.cubeInstancedFaces = cubeInstancedFaces;
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
        return worldChunk = new WorldChunk(cubeInstancedFaces, coordinate, generator);
    }
}