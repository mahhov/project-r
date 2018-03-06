package world.generator;

import util.math.MathNumbers;

public class WorldMap {
    public enum Terrain {
        DARK_RED(new float[] {.3f, 0f, 0f, 1f}, false), // dark red
        DARK_GRAY(new float[] {.4f, .3f, .3f, 1f}, false), // dark gray
        GRAY(new float[] {.5f, .5f, .5f, 1f}, false), // gray
        GREEN(new float[] {.5f, .6f, .5f, 1f}, false), // green
        LIGHT_GREEN(new float[] {.5f, .8f, .6f, 1f}, false), // light green
        TURQUOISE(new float[] {.5f, .8f, .8f, 1f}, false), // turquoise
        LIGHT_BLUE(new float[] {.5f, .8f, 1f, 1f}, false), // light blue
        BLUE_WHITE(new float[] {.9f, .9f, 1f, 1f}, false), // blue white
        WHITE(new float[] {1f, 1f, 1f, 1f}, false); // white

        public final float[] color;
        public final boolean tree;

        Terrain(float[] color, boolean tree) {
            this.color = color;
            this.tree = tree || true;
        }
    }

    private static final Terrain[] TERRAIN_VALUES = Terrain.values();
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

    public Terrain getTerrain(int x, int y) {
        int sum = (heightMap[x][y] - MIN_HEIGHT) * WEIGHT_HEIGHT + (environment[x][y] - MIN_ENVIRONMENT) * WEIGHT_ENVIRONMENT;
        int delta = DELTA_HEIGHT * WEIGHT_HEIGHT + DELTA_ENVIRONMENT * WEIGHT_ENVIRONMENT;
        int terrainIndex = MathNumbers.minMax(sum * (TERRAIN_VALUES.length - 1) / delta, 0, TERRAIN_VALUES.length - 1);
        return TERRAIN_VALUES[terrainIndex];
    }
}