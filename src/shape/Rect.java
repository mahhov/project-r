package shape;

import org.lwjgl.system.MemoryUtil;
import util.MathArrays;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Rect {
    private FloatBuffer verticesBuffer, colorsBuffer;
    private int vaoId;

    public Rect(float left, float top, float right, float bottom, float[] colors) {
        float[] vertices = new float[] {left, top, left, bottom, right, bottom, right, top};
        colors = MathArrays.repeatArray(colors, 4);

        createBuffers(vertices, colors);
        createVao();
    }

    private void createBuffers(float[] vertices, float[] colors) {
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
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
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
        glBindVertexArray(vaoId);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 4);
    }
}