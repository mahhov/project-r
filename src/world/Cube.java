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
    private float[][] vertices;
    private FloatBuffer[] buffers;
    private int[] vaoIds, vboIds;

    Cube(float x, float y, float z) {
        createVertices(x, y, z);
        createBuffers();
        createVaos();
    }

    private void createVertices(float x, float y, float z) {
        float left = x - .5f, right = x + .5f, front = y - .5f, back = y + .5f, top = z - .5f, bottom = z + .5f;

        vertices = new float[6][];

        vertices[LEFT] = new float[] {
                left, front, top,
                left, back, top,
                left, back, bottom,
                left, front, bottom
        };

        vertices[RIGHT] = new float[] {
                right, back, top,
                right, front, top,
                right, front, bottom,
                right, back, bottom
        };

        vertices[FRONT] = new float[] {
                left, front, top,
                left, front, bottom,
                right, front, bottom,
                right, front, top
        };

        vertices[BACK] = new float[] {
                right, back, top,
                right, back, bottom,
                left, back, bottom,
                left, back, top
        };

        vertices[BOTTOM] = new float[] {
                right, back, bottom,
                right, front, bottom,
                left, front, bottom,
                left, back, bottom
        };

        vertices[TOP] = new float[] {
                left, back, top,
                left, front, top,
                right, front, top,
                right, back, top
        };
    }

    private void createBuffers() {
        buffers = new FloatBuffer[6];

        for (int i = 0; i < 6; i++) {
            buffers[i] = MemoryUtil.memAllocFloat(12);
            buffers[i].put(vertices[i]).flip();
        }
    }

    private void createVaos() {
        vaoIds = new int[6];
        vboIds = new int[6];

        for (int i = 0; i < 6; i++) {
            vaoIds[i] = glGenVertexArrays();
            glBindVertexArray(vaoIds[i]);
            vboIds[i] = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vboIds[i]);
            glBufferData(GL_ARRAY_BUFFER, buffers[i], GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            glEnableVertexAttribArray(0);
        }
    }

    public void draw() {
        for (int i = 0; i < 6; i++) {
            glBindVertexArray(vaoIds[i]);
            glDrawArrays(GL_TRIANGLE_FAN, 0, 4);
        }
    }
}
