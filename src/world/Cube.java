package world;

import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

class Cube {
    private float[] vertices;
    private byte[] elements;
    private FloatBuffer verticesBuffer;
    private ByteBuffer elementsBuffer;
    private int vaoId;

    Cube(float x, float y, float z) {
        createVertices(x, y, z);
        createElements();
        createBuffers();
        createVao();
    }

    private void createVertices(float x, float y, float z) {
        float left = x - .5f, right = x + .5f, front = y - .5f, back = y + .5f, top = z - .5f, bottom = z + .5f;

        vertices = new float[] {
                left, front, top, // 0
                left, front, bottom, // 1
                left, back, top, // 2
                left, back, bottom, // 3
                right, front, top, // 4
                right, front, bottom, // 5
                right, back, top, // 6
                right, back, bottom, // 7
        };
    }

    private void createElements() {
        elements = new byte[] {
                0, 2, 3, 0, 3, 1, // left
                4, 7, 6, 4, 5, 7, // right
                0, 1, 5, 0, 5, 4, // front
                2, 3, 7, 2, 7, 6, // back
                0, 4, 6, 0, 6, 2, // top
                1, 5, 7, 1, 7, 3, // bottom
        };
    }

    private void createBuffers() {
        verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
        verticesBuffer.put(vertices).flip();

        elementsBuffer = MemoryUtil.memAlloc(elements.length);
        elementsBuffer.put(elements).flip();
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

        int elementsVboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementsVboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementsBuffer, GL_STATIC_DRAW);
        MemoryUtil.memFree(elementsBuffer);
    }

    void draw() {
        glBindVertexArray(vaoId);
        glDrawElements(GL_TRIANGLES, elements.length, GL_UNSIGNED_BYTE, 0);
    }
}
