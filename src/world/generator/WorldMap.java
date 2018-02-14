package world.generator;

import util.math.MathNumbers;

public class WorldMap {
    private static final int MAX_TERRAIN = 8;
    private static final int MIN_HEIGHT = 30, DELTA_HEIGHT = 140, WEIGHT_HEIGHT = 1;
    private static final int MIN_ENVIRONMENT = 20, DELTA_ENVIRONMENT = 60, WEIGHT_ENVIRONMENT = 5;

    public byte[][][] map;
    private int[][] heightMap;
    private byte[][] environment;

    WorldMap(byte[][][] map, int[][] heightMap, byte[][] environment) {
        this.map = map;
        this.heightMap = heightMap;
        this.environment = environment;
    }

    public int getTerrain(int x, int y) {
        int sum = (heightMap[x][y] - MIN_HEIGHT) * WEIGHT_HEIGHT + (environment[x][y] - MIN_ENVIRONMENT) * WEIGHT_ENVIRONMENT;
        int delta = DELTA_HEIGHT * WEIGHT_HEIGHT + DELTA_ENVIRONMENT * WEIGHT_ENVIRONMENT;
        return MathNumbers.minMax(sum * MAX_TERRAIN / delta, 0, MAX_TERRAIN);
    }
}