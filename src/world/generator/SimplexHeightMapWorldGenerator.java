package world.generator;

import util.MathNumbers;
import util.MathRandom;
import world.generator.simplex.SimplexNoiseHelper;

public class SimplexHeightMapWorldGenerator implements WorldGenerator {
    private final SimplexNoiseHelper simplexNoiseHelper;
    private final SimplexNoiseHelper simplexNoiseHelper2;
    private final int width, length, height;
    private final float heightMult;

    public SimplexHeightMapWorldGenerator(int width, int length, int height, int maxHeight) {
        simplexNoiseHelper = new SimplexNoiseHelper(1000, .5, MathRandom.random(0, Integer.MAX_VALUE));
        simplexNoiseHelper2 = new SimplexNoiseHelper(1000, .5, MathRandom.random(0, Integer.MAX_VALUE));
        this.width = width;
        this.length = length;
        this.height = height;
        heightMult = (maxHeight - 1) * .5f;
    }

    @Override
    public WorldMap generate(int startX, int startY, int startZ) {
        byte[][][] map = new byte[width + 2][length + 2][height + 2];
        int[][] heightMap = new int[width + 2][length + 2];
        byte[][] environment = new byte[width + 2][length + 2];

        for (int x = 0; x < width + 2; x++)
            for (int y = 0; y < length + 2; y++) {
                float noise = getNoise(x + startX - 1, y + startY - 1);
                int mapHeight = (int) ((noise + 1) * heightMult + 1);
                int relativeMapHeight = mapHeight - startZ + 1;
                int maxZ = MathNumbers.min(relativeMapHeight, height + 1);
                for (int z = 0; z <= maxZ; z++)
                    map[x][y][z] = 1;

                heightMap[x][y] = mapHeight;
                environment[x][y] = getNoise2(x + startX - 1, y + startY - 1);
            }

        return new WorldMap(map, heightMap, environment);
    }

    private float getNoise(int x, int y) {
        return MathNumbers.minMax((float) simplexNoiseHelper.getNoise(x, y), -1, 1);
    }

    private byte getNoise2(int x, int y) {
        float noise = MathNumbers.minMax((float) simplexNoiseHelper2.getNoise(x, y), -1, 1);
        return (byte) ((noise + 1) * 50);
    }
}