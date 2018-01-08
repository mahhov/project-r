package world.generator;

import util.MathNumbers;
import world.generator.simplex.SimplexNoiseHelper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SimplexHeightMapWorldGenerator {
    private static final int THREAD_POOL = 4;
    private static final SimplexNoiseHelper SIMPLEX_NOISE_HELPER = new SimplexNoiseHelper(1000, .5, 43);
    private float heightMult;
    private byte[][][] map;

    public byte[][][] generate(int width, int length, int height, int maxHeight) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);
        heightMult = (maxHeight - 1) * .5f;
        map = new byte[width][length][height];

        try {
            int step = width / THREAD_POOL;
            Future[] futures = new Future[THREAD_POOL];
            for (int i = 0; i < THREAD_POOL; i++)
                futures[i] = executor.submit(new ThreadedGenerator(i * step, i * step + step, 0, length));
            for (int i = 0; i < THREAD_POOL; i++)
                futures[i].get();
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    private static float getNoise(int x, int y) {
        return (float) SIMPLEX_NOISE_HELPER.getNoise(x, y);
    }

    private class ThreadedGenerator implements Callable<Void> {
        private final int startX, endX;
        private final int startY, endY;

        private ThreadedGenerator(int startX, int endX, int startY, int endY) {
            this.startX = startX;
            this.endX = endX;
            this.startY = startY;
            this.endY = endY;
        }

        @Override
        public Void call() throws Exception {
            for (int x = startX; x < endX; x++)
                for (int y = startY; y < endY; y++) {
                    float noise = MathNumbers.maxMin(getNoise(x, y), 1, -1);
                    int mapHeight = (int) ((noise + 1) * heightMult + 1);
                    for (int z = 0; z <= mapHeight; z++)
                        map[x][y][z] = 1;
                }

            return null;
        }
    }
}