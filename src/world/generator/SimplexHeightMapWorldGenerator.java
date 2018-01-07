package world.generator;

import util.MathNumbers;
import world.generator.simplex.SimplexNoiseHelper;

public class SimplexHeightMapWorldGenerator {
    public static int[][][] generate(int width, int length, int maxHeight) {
        final SimplexNoiseHelper simplexNoiseHelper = new SimplexNoiseHelper(1000, .5, 5000);
        final float heightMult = (maxHeight - 1) * .5f;

        int[][] heightMap = new int[width][length];
        for (int x = 0; x < width; x++)
            for (int y = 0; y < width; y++) {
                float noise = MathNumbers.maxMin((float) simplexNoiseHelper.getNoise(x, y), 1, -1);
                float height = (noise + 1) * heightMult + 1;
                heightMap[x][y] = (int) height;
            }

        return toIntArray(heightMap, maxHeight);
    }

    private static int[][][] toIntArray(int[][] height, int maxHeight) {
        int[][][] map = new int[height.length][height[0].length][maxHeight];
        for (int x = 0; x < height.length; x++)
            for (int y = 0; y < height[0].length; y++)
                for (int z = 0; z <= height[x][y]; z++)
                    map[x][y][z] = 1;
        return map;
    }
}