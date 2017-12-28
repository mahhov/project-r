package shape;

import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class CubeInstanced {
    private float[] vertices, colors;
    private byte[] indicies;
    private FloatBuffer verticesBuffer, colorsBuffer;
    private ByteBuffer indiciesBuffer;
    private int vaoId;

    public CubeInstanced(float x, float y, float z) {
        createVertices(x, y, z);
        createColors();
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

    private void createColors() {
        colors = new float[] {
                0, .6f, .3f, // left, front, top
                0, .6f, .3f, // left, front, bottom
                0, .6f, .3f, // left, back, top
                0, .6f, .3f, // left, back, bottom
                0, .6f, .3f, // right, front, top
                0, .6f, .3f, // right, front, bottom
                0, .6f, .3f, // right, back, top
                0, .6f, .3f, // right, back, bottom
        };
    }

    private void createElements() {
        indicies = new byte[] {
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

        colorsBuffer = MemoryUtil.memAllocFloat(colors.length);
        colorsBuffer.put(colors).flip();

        indiciesBuffer = MemoryUtil.memAlloc(indicies.length);
        indiciesBuffer.put(indicies).flip();
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

        int elementsVboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementsVboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indiciesBuffer, GL_STATIC_DRAW);
        MemoryUtil.memFree(indiciesBuffer);
    }

    public void draw() {
        glBindVertexArray(vaoId);
        glDrawElements(GL_TRIANGLES, indicies.length, GL_UNSIGNED_BYTE, 0);
    }
}