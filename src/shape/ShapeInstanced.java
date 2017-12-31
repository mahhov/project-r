package shape;

import org.lwjgl.system.MemoryUtil;
import util.LList;
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

class ShapeInstanced {
    private int numIndicies;
    private FloatBuffer verticesBuffer, colorsBuffer, normalsBuffer;
    private ByteBuffer indiciesBuffer;
    private int vaoId;

    private LList<SimpleMatrix4f> models;
    private FloatBuffer modelsBuffer;

    ShapeInstanced(float[] vertices, float[] colors, float[] normals, byte[] indicies) {
        numIndicies = indicies.length;
        createBuffers(vertices, colors, normals, indicies);
        createVao();

        models = new LList<>();
    }

    private void createBuffers(float[] vertices, float[] colors, float[] normals, byte[] indicies) {
        verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
        verticesBuffer.put(vertices).flip();

        colorsBuffer = MemoryUtil.memAllocFloat(colors.length);
        colorsBuffer.put(colors).flip();

        normalsBuffer = MemoryUtil.memAllocFloat(normals.length);
        normalsBuffer.put(normals).flip();

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

        int normalsVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, normalsVboId);
        glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(6, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(6);
        MemoryUtil.memFree(normalsBuffer);

        int elementsVboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementsVboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indiciesBuffer, GL_STATIC_DRAW);
        MemoryUtil.memFree(indiciesBuffer);
    }

    void add(float x, float y, float z) {
        models.addTail(SimpleMatrix4f.translate(x, y, z));
    }

    void doneAdding() {
        glBindVertexArray(vaoId);

        modelsBuffer = MemoryUtil.memAllocFloat(models.size() * 16);
        for (SimpleMatrix4f model : models)
            model.toBufferSub(modelsBuffer);
        modelsBuffer.flip();

        int floatBytes = Float.SIZE / Byte.SIZE;
        int modelsVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, modelsVboId);
        glBufferData(GL_ARRAY_BUFFER, modelsBuffer, GL_STATIC_DRAW);

        for (int i = 0; i < 4; i++) {
            int loc = i + 1;
            glVertexAttribPointer(loc, 4, GL_FLOAT, false, 16 * floatBytes, i * 4 * floatBytes);
            glVertexAttribDivisor(loc, 1);
            glEnableVertexAttribArray(loc);
        }

        MemoryUtil.memFree(modelsBuffer);
    }

    void reset() {
        models.removeAll();
    }

    void draw() {
        glBindVertexArray(vaoId);
        glDrawElementsInstanced(GL_TRIANGLES, numIndicies, GL_UNSIGNED_BYTE, 0, models.size());
    }
}