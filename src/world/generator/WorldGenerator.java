package world.generator;

import util.MathNumbers;
import util.MathRandom;

public class WorldGenerator {
    public static int[][][] generate(int width, int length, int maxHeight) {
        width = MathNumbers.powerOf2(width) + 1;
        length = MathNumbers.powerOf2(length) + 1;
        int size = MathNumbers.max(width, length);
        double[][] height = heightMap(size, maxHeight);
        return toIntArray(height, maxHeight);
    }

    private static double[][] heightMap(int size, int height) {
        // credit to M. Jessup http://stackoverflow.com/questions/2755750/diamond-square-algorithm

        double[][] heightMap = new double[size][size];
        float half = height * .5f;
        heightMap[0][0] = heightMap[size - 1][0] = heightMap[0][size - 1] = heightMap[size - 1][size - 1] = half;

        for (int sideLength = size - 1; sideLength >= 2; sideLength /= 2, half /= 2) {
            int halfSide = sideLength / 2;
            for (int x = 0; x < size - 1; x += sideLength)
                for (int y = 0; y < size - 1; y += sideLength) {
                    double avg = (heightMap[x][y] + heightMap[x + sideLength][y] + heightMap[x][y + sideLength] + heightMap[x + sideLength][y + sideLength]);
                    avg *= .25;
                    heightMap[x + halfSide][y + halfSide] = avg + MathRandom.random(-half, half);
                }
            for (int x = 0; x < size - 1; x += halfSide)
                for (int y = (x + halfSide) % sideLength; y < size - 1; y += sideLength) {
                    double avg = heightMap[(x - halfSide + size) % size][y] + heightMap[(x + halfSide) % size][y] + heightMap[x][(y + halfSide) % size] + heightMap[x][(y - halfSide + size) % size];
                    avg *= .25;
                    heightMap[x][y] = avg = avg + MathRandom.random(-half, half);
                    if (x == 0)
                        heightMap[size - 1][y] = avg;
                    if (y == 0)
                        heightMap[x][size - 1] = avg;
                }
        }

        return heightMap;
    }

    private static int[][][] toIntArray(double[][] height, int maxHeight) {
        int[][][] map = new int[height.length][height[0].length][maxHeight];
        for (int x = 0; x < height.length; x++)
            for (int y = 0; y < height[0].length; y++)
                for (int z = 0; z < height[x][y] + 1; z++)
                    map[x][y][z] = 1;
        return map;
    }
}