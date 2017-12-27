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
    private static final int LEFT = 0, RIGHT = 1, FRONT = 2, BACK = 3, BOTTOM = 4, TOP = 5;
    private float[] vertices;
    private FloatBuffer buffer;
    private int vaoId, vboId;

    Cube(float x, float y, float z) {
        createVertices(x, y, z);
        createBuffers();
        createVaos();
    }

    private void createVertices(float x, float y, float z) {
        float left = x - .5f, right = x + .5f, front = y - .5f, back = y + .5f, top = z - .5f, bottom = z + .5f;

        vertices = new float[6 * 6 * 3];

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

    private void createBuffers() {
        buffer = MemoryUtil.memAllocFloat(vertices.length);
        buffer.put(vertices).flip();
    }

    private void createVaos() {
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);
        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);
    }

    public void draw() {
        for (int i = 0; i < 6; i++) {
            glBindVertexArray(vaoId);
            glDrawArrays(GL_TRIANGLES, 0, 36);
        }
    }
}
