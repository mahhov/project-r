package world.generator;

import world.generator.simplex.SimplexNoiseHelper;

public class Simplex3DWorldGenerator {
    private static final SimplexNoiseHelper SIMPLEX_NOISE_HELPER = new SimplexNoiseHelper(1000 / 4, .5, 3);
    private static final int RESOLUTION = 4, RESOLUTION_VERT = 2;

    public static int[][][] generate(int width, int length, int height, int maxHeight) {
        int[][][] map = new int[width][length][height];
        for (int x = 0; x < width; x++)
            for (int y = 0; y < length; y++)
                for (int z = 0; z < 2; z++)
                    map[x][y][z] = 1;

        for (int x = 0; x < width; x += RESOLUTION)
            for (int y = 0; y < length; y += RESOLUTION)
                for (int z = 2; z < maxHeight; z += RESOLUTION_VERT)
                    if (getNoise(x / RESOLUTION, y / RESOLUTION, z / RESOLUTION_VERT))
                        for (int xx = x; xx < x + RESOLUTION; xx++)
                            for (int yy = y; yy < y + RESOLUTION; yy++)
                                for (int zz = z; zz < z + RESOLUTION_VERT; zz++)
                                    map[xx][yy][zz] = 1;

        return map;
    }

    private static boolean getNoise(int x, int y, int z) {
        return (float) SIMPLEX_NOISE_HELPER.getNoise(x, y, z) > 0;
    }
}