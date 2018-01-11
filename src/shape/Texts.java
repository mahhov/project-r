package shape;

import org.lwjgl.system.MemoryUtil;
import util.LList;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Texts {
    private FloatBuffer verticesBuffer, textureCoordinatesBuffer;
    private int verticesVboId, textureCoordinatesVboId;
    private int vaoId;

    private LList<Text> texts;
    private int numTexts;

    public Texts(int maxNumTexts) {
        texts = new LList<>();
        createBuffers(maxNumTexts * 6);
        createVao();
    }

    private void createBuffers(int maxNumVertices) {
        verticesBuffer = MemoryUtil.memAllocFloat(maxNumVertices * 2);
        textureCoordinatesBuffer = MemoryUtil.memAllocFloat(maxNumVertices * 2);
    }

    private void createVao() {
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        verticesVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, verticesVboId);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        textureCoordinatesVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, textureCoordinatesVboId);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(1);
    }

    public Text addText() {
        Text text = new Text();
        texts.addTail(text);
        return text;
    }

    public void doneAdding() {
        fillBuffers();
        updateVbos();
    }

    private void fillBuffers() {
        verticesBuffer.clear();
        textureCoordinatesBuffer.clear();
        numTexts = 0;
        for (Text text : texts)
            if (!text.disabled) {
                numTexts++;
                verticesBuffer.put(text.vertices);
                textureCoordinatesBuffer.put(text.textureCoordinates);
            }
        verticesBuffer.flip();
        textureCoordinatesBuffer.flip();
    }

    public void draw() {
        glBindVertexArray(vaoId);
        glDrawArrays(GL_TRIANGLES, 0, numTexts * 6);
    }

    private void updateVbos() {
        glBindVertexArray(vaoId);

        glBindBuffer(GL_ARRAY_BUFFER, verticesVboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, textureCoordinatesVboId);
        glBufferData(GL_ARRAY_BUFFER, textureCoordinatesBuffer, GL_STATIC_DRAW);
    }

    public static class Text {
        private float[] vertices, textureCoordinates;
        private boolean disabled;

        private Text() {
        }

        public void setCoordinates(float left, float top, float right, float bottom) {
            vertices = new float[] {left, top, left, bottom, right, bottom, right, top, left, top, right, bottom};
        }

        public void setText(String text) {
            textureCoordinates = StringTexture.getCharacterCordinates(text.toCharArray()[0]);
        }

        public void disable() {
            disabled = true;
        }

        public void enable() {
            disabled = false;
        }
    }
}