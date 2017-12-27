package world;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Cube {
    private float[] vertices, colors;
    private FloatBuffer verticesBuffer, colorsBuffer;
    private int vaoId;

    Cube(float x, float y, float z) {
        createVertices(x, y, z);
        createColors();
        createBuffers();
        createVao();
    }

    private void createVertices(float x, float y, float z) {
        float left = x - .5f, right = x + .5f, front = y - .5f, back = y + .5f, top = z - .5f, bottom = z + .5f;

        vertices = new float[] {
                left, front, top,
                left, back, top,
                left, back, bottom,
                left, front, top,
                left, back, bottom,
                left, front, bottom,
                right, back, top,
                right, front, top,
                right, front, bottom,
                right, back, top,
                right, front, bottom,
                right, back, bottom,
                left, front, top,
                left, front, bottom,
                right, front, bottom,
                left, front, top,
                right, front, bottom,
                right, front, top,
                right, back, top,
                right, back, bottom,
                left, back, bottom,
                right, back, top,
                left, back, bottom,
                left, back, top,
                right, back, bottom,
                right, front, bottom,
                left, front, bottom,
                right, back, bottom,
                left, front, bottom,
                left, back, bottom,
                left, back, top,
                left, front, top,
                right, front, top,
                left, back, top,
                right, front, top,
                right, back, top
        };
    }

    private void createColors() {
        float[][] color = new float[6][];
        color[0] = new float[] {1, 0, 0};
        color[1] = new float[] {0, 1, 0};
        color[2] = new float[] {1, 0, 1};
        color[3] = new float[] {1, 1, 0};
        color[4] = new float[] {0, 0, 1};
        color[5] = new float[] {1, 1, 1};

        colors = new float[vertices.length];
        for (int side = 0; side < 6; side++) {
            for (int point = 0; point < 6; point++)
                for (int rgb = 0; rgb < 3; rgb++)
                    colors[side * 18 + point * 3 + rgb] = color[side][rgb];
        }
    }

    private void createBuffers() {
        verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
        verticesBuffer.put(vertices).flip();

        colorsBuffer = MemoryUtil.memAllocFloat(colors.length);
        colorsBuffer.put(colors).flip();
    }

    private void createVao() {
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        int verticesVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, verticesVboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);
        MemoryUtil.memFree(verticesBuffer);

        int colorsVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, colorsVboId);
        glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(1);
        MemoryUtil.memFree(colorsBuffer);
    }

    public void draw() {
        for (int i = 0; i < 6; i++) {
            glBindVertexArray(vaoId);
            glDrawArrays(GL_TRIANGLES, 0, 36);
        }
    }
}
