package world.generator;

import util.MathNumbers;
import world.generator.simplex.SimplexNoiseHelper;

public class SimplexHeightMapWorldGenerator {
    private static final SimplexNoiseHelper SIMPLEX_NOISE_HELPER = new SimplexNoiseHelper(1000, .5, 43);

    private final int width, length, height;
    private final float heightMult;

    public SimplexHeightMapWorldGenerator(int width, int length, int height, int maxHeight) {
        this.width = width;
        this.length = length;
        this.height = height;
        heightMult = (maxHeight - 1) * .5f;
    }

    public byte[][][] generate(int startX, int startY, int startZ) {
        byte[][][] map = new byte[width + 2][length + 2][height + 2];

        for (int x = 0; x < width + 2; x++)
            for (int y = 0; y < length + 2; y++) {
                float noise = getNoise(x + startX - 1, y + startY - 1);
                int mapHeight = (int) ((noise + 1) * heightMult + 1);
                int relativeMapHeight = mapHeight - startZ + 1;
                int maxZ = MathNumbers.min(relativeMapHeight, height + 1);
                for (int z = 0; z <= maxZ; z++)
                    map[x][y][z] = 1;
            }

        return map;
    }

    private static float getNoise(int x, int y) {
        return MathNumbers.maxMin((float) SIMPLEX_NOISE_HELPER.getNoise(x, y), 1, -1);
    }
}