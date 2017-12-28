package shape;

import org.lwjgl.system.MemoryUtil;
import util.lwjgl.SimpleMatrix4f;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class CubeInstanced {
    private float[] vertices, colors;
    private byte[] indicies;
    private FloatBuffer verticesBuffer, colorsBuffer;
    private ByteBuffer indiciesBuffer;
    private int vaoId;

    private int instances;
    private SimpleMatrix4f[] models;
    private FloatBuffer modelsBuffer;

    public CubeInstanced(int maxInstances) {
        createVertices();
        createColors();
        createElements();
        createBuffers();
        createVao();

        models = new SimpleMatrix4f[maxInstances];
    }

    private void createVertices() {
        float s = 1f;

        vertices = new float[] {
                -s, -s, -s, // 0 : (left, front, top)
                -s, -s, s, // 1 : (left, front, bottom)
                -s, s, -s, // 2 : (left, back, top)
                -s, s, s, // 3 : (left, back, bottom)
                s, -s, -s, // 4 : (right, front, top)
                s, -s, s, // 5 : (right, front, bottom)
                s, s, -s, // 6 : (right, back, top)
                s, s, s, // 7 : (right, back, bottom)
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
        glVertexAttribPointer(5, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(5);
        MemoryUtil.memFree(colorsBuffer);

        int elementsVboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementsVboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indiciesBuffer, GL_STATIC_DRAW);
        MemoryUtil.memFree(indiciesBuffer);
    }

    public void add(float x, float y, float z) {
        models[instances++] = SimpleMatrix4f.translate(x * 2, y * 2, z * 2);
    }

    public void doneAdding() {
        glBindVertexArray(vaoId);

        modelsBuffer = MemoryUtil.memAllocFloat(instances * 16);
        for (int i = 0; i < instances; i++)
            models[i].toBufferSub(modelsBuffer);
        modelsBuffer.flip();

        int floatBytes = Float.SIZE / Byte.SIZE;
        int modelsVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, modelsVboId);
        glBufferData(GL_ARRAY_BUFFER, modelsBuffer, GL_STATIC_DRAW);

        for (int i = 0; i < 4; i++) {
            int loc = i + 1;
            glVertexAttribPointer(loc, 4, GL_FLOAT, false, 16 * floatBytes, i * 4 * floatBytes);
            glEnableVertexAttribArray(loc);
            glVertexAttribDivisor(loc, 1);
        }

        MemoryUtil.memFree(modelsBuffer);
    }

    public int draw() {
        glBindVertexArray(vaoId);
        glDrawElementsInstanced(GL_TRIANGLES, indicies.length, GL_UNSIGNED_BYTE, 0, instances);
        return instances;
    }
}