package shape;

import util.MathArrays;
import util.SimpleMatrix4f;

public class CubeInstancedFaces {
    public static final int LEFT_SIDE = 0, RIGHT_SIDE = 1, FRONT_SIDE = 2, BACK_SIDE = 3, TOP_SIDE = 4, BOTTOM_SIDE = 5;

    private static final float S = .5f;
    private static final float[] VERTICIES = {
            -S, S, S, // 0 : (left, front, top)
            -S, -S, S, // 1 : (left, front, bottom)
            -S, S, -S, // 2 : (left, back, top)
            -S, -S, -S, // 3 : (left, back, bottom)
            S, S, S, // 4 : (right, front, top)
            S, -S, S, // 5 : (right, front, bottom)
            S, S, -S, // 6 : (right, back, top)
            S, -S, -S, // 7 : (right, back, bottom)
    };

    private static final float[][] SIDE_VERTICIES = new float[6][], SIDE_COLORS = new float[6][], SIDE_NORMALS = new float[6][];
    private static final byte[] INDICIES = new byte[] {0, 2, 3, 1, 0, 3};

    static {
        SIDE_VERTICIES[LEFT_SIDE] = MathArrays.pluckArray3(VERTICIES, new int[] {2, 0, 3, 1});
        SIDE_VERTICIES[RIGHT_SIDE] = MathArrays.pluckArray3(VERTICIES, new int[] {4, 6, 5, 7});
        SIDE_VERTICIES[FRONT_SIDE] = MathArrays.pluckArray3(VERTICIES, new int[] {0, 4, 1, 5});
        SIDE_VERTICIES[BACK_SIDE] = MathArrays.pluckArray3(VERTICIES, new int[] {6, 2, 7, 3});
        SIDE_VERTICIES[TOP_SIDE] = MathArrays.pluckArray3(VERTICIES, new int[] {2, 6, 0, 4});
        SIDE_VERTICIES[BOTTOM_SIDE] = MathArrays.pluckArray3(VERTICIES, new int[] {1, 5, 3, 7});

        SIDE_COLORS[LEFT_SIDE] = MathArrays.repeatArray(new float[] {1, 1, 1}, 4);
        SIDE_COLORS[RIGHT_SIDE] = SIDE_COLORS[LEFT_SIDE];
        SIDE_COLORS[FRONT_SIDE] = SIDE_COLORS[LEFT_SIDE];
        SIDE_COLORS[BACK_SIDE] = SIDE_COLORS[LEFT_SIDE];
        SIDE_COLORS[TOP_SIDE] = SIDE_COLORS[LEFT_SIDE];
        SIDE_COLORS[BOTTOM_SIDE] = SIDE_COLORS[LEFT_SIDE];

        SIDE_NORMALS[LEFT_SIDE] = MathArrays.repeatArray(new float[] {-1, 0, 0}, 4);
        SIDE_NORMALS[RIGHT_SIDE] = MathArrays.repeatArray(new float[] {1, 0, 0}, 4);
        SIDE_NORMALS[FRONT_SIDE] = MathArrays.repeatArray(new float[] {0, 0, 1}, 4);
        SIDE_NORMALS[BACK_SIDE] = MathArrays.repeatArray(new float[] {0, 0, -1}, 4);
        SIDE_NORMALS[TOP_SIDE] = MathArrays.repeatArray(new float[] {0, 1, 0}, 4);
        SIDE_NORMALS[BOTTOM_SIDE] = MathArrays.repeatArray(new float[] {0, -1, 0}, 4);
    }

    private ShapeInstanced[] sides;

    public CubeInstancedFaces() {
        sides = new ShapeInstanced[6];
        for (int i = 0; i < sides.length; i++)
            sides[i] = new ShapeInstanced(SIDE_VERTICIES[i], SIDE_COLORS[i], SIDE_NORMALS[i], INDICIES);
    }

    public CubeInstancedFaces(float[] color) { // todo accept color per cube
        color = MathArrays.repeatArray(color, 4);

        sides = new ShapeInstanced[6];
        for (int i = 0; i < sides.length; i++)
            sides[i] = new ShapeInstanced(SIDE_VERTICIES[i], color, SIDE_NORMALS[i], INDICIES);
    }

    public void add(float x, float y, float z) {
        add(SimpleMatrix4f.translate(x, y, z));
    }

    public void add(float x, float y, float z, boolean sides[]) {
        add(SimpleMatrix4f.translate(x, y, z), sides);
    }

    public void add(float x, float y, float z, float theta, float thetaZ) {
        add(SimpleMatrix4f.modelMatrix(x, y, z, theta, thetaZ));
    }

    public void add(float x, float y, float z, float theta, float thetaZ, boolean sides[]) {
        add(SimpleMatrix4f.modelMatrix(x, y, z, theta, thetaZ), sides);
    }

    public void add(float x, float y, float z, float theta, float thetaZ, float scale) {
        add(SimpleMatrix4f.modelMatrix(x, y, z, theta, thetaZ, scale));
    }

    private void add(SimpleMatrix4f modelMatrix) {
        for (int i = 0; i < 6; i++)
            sides[i].add(modelMatrix);
    }

    private void add(SimpleMatrix4f modelMatrix, boolean sides[]) {
        for (int i = 0; i < 6; i++)
            if (sides[i])
                this.sides[i].add(modelMatrix);
    }

    public void doneAdding() {
        for (ShapeInstanced side : sides)
            side.doneAdding();
    }

    public void reset() {
        for (ShapeInstanced side : sides)
            side.reset();
    }

    public void draw() {
        for (ShapeInstanced side : sides)
            side.draw();
    }
}