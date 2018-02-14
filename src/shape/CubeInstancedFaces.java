package shape;

import util.math.MathArrays;
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

    private static final float[][] SIDE_VERTICIES = new float[6][], SIDE_NORMALS = new float[6][];
    private static final byte[] INDICIES = new byte[] {0, 2, 3, 1, 0, 3};
    private static final byte[] INDICIES_FLIPPED = new byte[] {0, 3, 2, 1, 3, 0};

    static {
        SIDE_VERTICIES[LEFT_SIDE] = MathArrays.pluckArray3(VERTICIES, new int[] {2, 0, 3, 1});
        SIDE_VERTICIES[RIGHT_SIDE] = MathArrays.pluckArray3(VERTICIES, new int[] {4, 6, 5, 7});
        SIDE_VERTICIES[FRONT_SIDE] = MathArrays.pluckArray3(VERTICIES, new int[] {0, 4, 1, 5});
        SIDE_VERTICIES[BACK_SIDE] = MathArrays.pluckArray3(VERTICIES, new int[] {6, 2, 7, 3});
        SIDE_VERTICIES[TOP_SIDE] = MathArrays.pluckArray3(VERTICIES, new int[] {2, 6, 0, 4});
        SIDE_VERTICIES[BOTTOM_SIDE] = MathArrays.pluckArray3(VERTICIES, new int[] {1, 5, 3, 7});

        SIDE_NORMALS[LEFT_SIDE] = MathArrays.repeatArray(new float[] {-1, 0, 0}, 4);
        SIDE_NORMALS[RIGHT_SIDE] = MathArrays.repeatArray(new float[] {1, 0, 0}, 4);
        SIDE_NORMALS[FRONT_SIDE] = MathArrays.repeatArray(new float[] {0, 0, 1}, 4);
        SIDE_NORMALS[BACK_SIDE] = MathArrays.repeatArray(new float[] {0, 0, -1}, 4);
        SIDE_NORMALS[TOP_SIDE] = MathArrays.repeatArray(new float[] {0, 1, 0}, 4);
        SIDE_NORMALS[BOTTOM_SIDE] = MathArrays.repeatArray(new float[] {0, -1, 0}, 4);
    }

    private ShapeInstanced[] sides, sidesFlipped;

    public CubeInstancedFaces() {
        sides = new ShapeInstanced[6];
        for (int i = 0; i < sides.length; i++)
            sides[i] = new ShapeInstanced(SIDE_VERTICIES[i], SIDE_NORMALS[i], INDICIES);

        sidesFlipped = new ShapeInstanced[6];
        for (int i = 0; i < sidesFlipped.length; i++)
            sidesFlipped[i] = new ShapeInstanced(SIDE_VERTICIES[i], SIDE_NORMALS[i], INDICIES_FLIPPED);
    }

    // xyz, theta(z), sides, scale, scaleHeight, color, flipNormals

    public void add(float x, float y, float z, float theta, float thetaZ, float scale, float[] color) {
        add(SimpleMatrix4f.modelMatrix(x, y, z, theta, thetaZ, scale, scale, scale), color);
    }

    public void add(float x, float y, float z, float theta, float thetaZ, float scaleX, float scaleY, float scaleZ, float[] color, boolean flipNormals) {
        add(SimpleMatrix4f.modelMatrix(x, y, z, theta, thetaZ, scaleX, scaleY, scaleZ), color, flipNormals);
    }

    public void add(float x, float y, float z, boolean sides[], float[] color) {
        add(SimpleMatrix4f.translate(x, y, z), sides, color);
    }

    private void add(SimpleMatrix4f modelMatrix, boolean sides[], float[] color) {
        for (int i = 0; i < 6; i++)
            if (sides[i])
                this.sides[i].add(modelMatrix, color);
    }

    private void add(SimpleMatrix4f modelMatrix, float[] color) {
        for (int i = 0; i < 6; i++)
            sides[i].add(modelMatrix, color);
    }

    private void add(SimpleMatrix4f modelMatrix, float[] color, boolean flipNormals) {
        if (flipNormals)
            for (int i = 0; i < 6; i++)
                sidesFlipped[i].add(modelMatrix, color);
        else
            for (int i = 0; i < 6; i++)
                sides[i].add(modelMatrix, color);
    }

    public void doneAdding() {
        for (int i = 0; i < sides.length; i++) {
            sides[i].doneAdding();
            sidesFlipped[i].doneAdding();
        }
    }

    public void reset() {
        for (int i = 0; i < sides.length; i++) {
            sides[i].reset();
            sidesFlipped[i].reset();
        }
    }

    public void draw() {
        for (int i = 0; i < sides.length; i++) {
            sides[i].draw();
            sidesFlipped[i].draw();
        }
    }
}