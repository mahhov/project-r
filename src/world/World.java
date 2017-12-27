package world;

public class World {
    private Cube cube;

    public World(int width, int length, int height, int programId) {
        cube = new Cube(0, 0, -10);
    }

    public void update(int programId) {
    }

    public void draw(int programId) {
        cube.draw();
    }
}