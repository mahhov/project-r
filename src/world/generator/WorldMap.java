package world.generator;

import util.MathNumbers;

public class WorldMap {
    public byte[][][] map;
    public int[][] heightMap;
    public byte[][] environment;

    WorldMap(byte[][][] map, int[][] heightMap, byte[][] environment) {
        this.map = map;
        this.heightMap = heightMap;
        this.environment = environment;
    }

    public int getTerrain(int x, int y) {
        return MathNumbers.minMax((heightMap[x][y] + environment[x][y] - 80) / 10, 0, 8); // todo make constants
    }
}