package world.generator;

import world.generator.simplex.SimplexNoiseHelper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Simplex3DWorldGenerator {
    // todo fix need for threadpool, resolution, and width to be divisible
    private static final int THREAD_POOL = 4;
    private static final int RESOLUTION = 2, RESOLUTION_VERT = 2;
    private static final SimplexNoiseHelper SIMPLEX_NOISE_HELPER = new SimplexNoiseHelper(1000 / RESOLUTION, .5, 43);

    private int length, maxHeight;
    private byte map[][][];

    public byte[][][] generate(int width, int length, int height, int maxHeight) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);
        this.length = length;
        this.maxHeight = maxHeight;
        map = new byte[width][length][height];

        for (int x = 0; x < width; x++)
            for (int y = 0; y < length; y++)
                for (int z = 0; z < 2; z++)
                    map[x][y][z] = 1;

        try {
            int step = width / THREAD_POOL;
            Future[] futures = new Future[THREAD_POOL];
            for (int i = 0; i < THREAD_POOL; i++)
                futures[i] = executor.submit(new ThreadedGenerator(i * step, i * step + step));
            for (int i = 0; i < THREAD_POOL; i++)
                futures[i].get();
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    private static boolean getNoise(int x, int y, int z) {
        return (float) SIMPLEX_NOISE_HELPER.getNoise(x, y, z) > 0;
    }

    private class ThreadedGenerator implements Callable<Void> {
        private final int startX, endX;

        private ThreadedGenerator(int startX, int endX) {
            this.startX = startX;
            this.endX = endX;
        }

        @Override
        public Void call() throws Exception {
            for (int x = startX; x < endX; x += RESOLUTION)
                for (int y = 0; y < length; y += RESOLUTION)
                    for (int z = 2; z < maxHeight; z += RESOLUTION_VERT)
                        if (getNoise(x / RESOLUTION, y / RESOLUTION, z / RESOLUTION_VERT))
                            for (int xx = x; xx < x + RESOLUTION; xx++)
                                for (int yy = y; yy < y + RESOLUTION; yy++)
                                    for (int zz = z; zz < z + RESOLUTION_VERT; zz++)
                                        map[xx][yy][zz] = 1;

            return null;
        }
    }
}