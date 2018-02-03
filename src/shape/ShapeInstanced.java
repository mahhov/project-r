package shape;

import org.lwjgl.system.MemoryUtil;
import util.LList;
import util.SimpleMatrix4f;

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
    private static final int FLOAT_BYTES = Float.SIZE / Byte.SIZE;

    private int numIndicies;
    private FloatBuffer verticesBuffer, normalsBuffer;
    private ByteBuffer indiciesBuffer;
    private int vaoId;

    private int modelsVboId, colorsVboId;
    private LList<InstanceDetail> instanceDetails;

    ShapeInstanced(float[] vertices, float[] normals, byte[] indicies) {
        numIndicies = indicies.length;
        createBuffers(vertices, normals, indicies);
        createVao();

        instanceDetails = new LList<>();
    }

    private void createBuffers(float[] vertices, float[] normals, byte[] indicies) {
        verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
        verticesBuffer.put(vertices).flip();

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

        modelsVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, modelsVboId);
        for (int i = 0; i < 4; i++) {
            int loc = i + 1;
            glVertexAttribPointer(loc, 4, GL_FLOAT, false, 16 * FLOAT_BYTES, i * 4 * FLOAT_BYTES);
            glVertexAttribDivisor(loc, 1);
            glEnableVertexAttribArray(loc);
        }

        colorsVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, colorsVboId);
        glVertexAttribPointer(5, 4, GL_FLOAT, false, 0, 0);
        glVertexAttribDivisor(5, 1);
        glEnableVertexAttribArray(5);
    }

    void add(SimpleMatrix4f modelMatrix, float[] color) {
        instanceDetails.addTail(new InstanceDetail(modelMatrix, color));
    }

    void doneAdding() {
        glBindVertexArray(vaoId);

        FloatBuffer modelsBuffer = MemoryUtil.memAllocFloat(instanceDetails.size() * 16); // todo reuse same buffer?
        FloatBuffer colorsBuffer = MemoryUtil.memAllocFloat(instanceDetails.size() * 4);
        for (InstanceDetail instanceDetail : instanceDetails) {
            instanceDetail.modelMatrix.toBufferSub(modelsBuffer);
            colorsBuffer.put(instanceDetail.colors);
        }
        modelsBuffer.flip();
        colorsBuffer.flip();

        glBindBuffer(GL_ARRAY_BUFFER, modelsVboId);
        glBufferData(GL_ARRAY_BUFFER, modelsBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, colorsVboId);
        glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);

        MemoryUtil.memFree(modelsBuffer);
        MemoryUtil.memFree(colorsBuffer);
    }

    void reset() {
        instanceDetails.removeAll();
    }

    void draw() {
        glBindVertexArray(vaoId);
        glDrawElementsInstanced(GL_TRIANGLES, numIndicies, GL_UNSIGNED_BYTE, 0, instanceDetails.size());
    }

    private class InstanceDetail {
        private SimpleMatrix4f modelMatrix;
        private float[] colors;

        private InstanceDetail(SimpleMatrix4f modelMatrix, float[] colors) {
            this.modelMatrix = modelMatrix;
            this.colors = colors;
        }
    }
}