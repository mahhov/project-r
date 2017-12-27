package world;

import util.MathRandom;

public class World {
    private Cube[][][] cubes;

    public World(int width, int length, int height) {
        cubes = new Cube[width][length][height];
        for (int x = 0; x < width; x++)
            for (int y = 0; y < length; y++) {
                int h = MathRandom.random(1, height - 1);
                for (int z = 0; z < h; z++)
                    cubes[x][y][z] = new Cube(x + .5f - width / 2f, z + .5f - length / 2f, y + .5f - width);
            }
    }

    public void update() {
    }

    public void draw() {
        for (int x = 0; x < cubes.length; x++)
            for (int y = 0; y < cubes[0].length; y++)
                for (int z = 0; z < cubes[0][0].length; z++)
                    if (cubes[x][y][z] != null)
                        cubes[x][y][z].draw();
    }
}