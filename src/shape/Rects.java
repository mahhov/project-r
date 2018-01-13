package shape;

import org.lwjgl.system.MemoryUtil;
import util.LList;
import util.MathArrays;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Rects {
    private FloatBuffer verticesBuffer, colorsBuffer;
    private int verticesVboId, colorsVboId;
    private int vaoId;

    private LList<Rect> rects;
    private int numRects;

    public Rects(int maxNumRects) {
        rects = new LList<>();
        createBuffers(maxNumRects * 6);
        createVao();
    }

    private void createBuffers(int maxNumVertices) {
        verticesBuffer = MemoryUtil.memAllocFloat(maxNumVertices * 2);
        colorsBuffer = MemoryUtil.memAllocFloat(maxNumVertices * 4);
    }

    private void createVao() {
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        verticesVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, verticesVboId);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        colorsVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, colorsVboId);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(1);
    }

    public Rect addRect(float[] color) {
        Rect rect = new Rect(color);
        rects.addTail(rect);
        return rect;
    }

    public void doneAdding() {
        fillBuffers();
        updateVbos();
    }

    private void fillBuffers() {
        verticesBuffer.clear();
        colorsBuffer.clear();
        numRects = 0;
        for (Rect rect : rects)
            if (rect.enabled()) {
                numRects++;
                verticesBuffer.put(rect.vertices);
                colorsBuffer.put(rect.colors);
            }
        verticesBuffer.flip();
        colorsBuffer.flip();
    }

    public void draw() {
        glBindVertexArray(vaoId);
        glDrawArrays(GL_TRIANGLES, 0, numRects * 6);
    }

    private void updateVbos() {
        glBindVertexArray(vaoId);

        glBindBuffer(GL_ARRAY_BUFFER, verticesVboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, colorsVboId);
        glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);
    }

    public static class Rect extends BasicShape {
        private Rect(float[] color) {
            colors = MathArrays.repeatArray(color, 6);
        }
    }
}