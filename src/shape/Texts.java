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
    private int numCharacters;

    public Texts(int maxNumCharacters) {
        texts = new LList<>();
        createBuffers(maxNumCharacters * 6);
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
        numCharacters = 0;
        for (Text text : texts)
            if (!text.disabled) {
                numCharacters += text.characters.size();
                for (Character character : text.characters) {
                    verticesBuffer.put(character.vertices);
                    textureCoordinatesBuffer.put(character.textureCoordinates);
                }
            }
        verticesBuffer.flip();
        textureCoordinatesBuffer.flip();
    }

    public void draw() {
        glBindVertexArray(vaoId);
        glDrawArrays(GL_TRIANGLES, 0, numCharacters * 6);
    }

    private void updateVbos() {
        glBindVertexArray(vaoId);

        glBindBuffer(GL_ARRAY_BUFFER, verticesVboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, textureCoordinatesVboId);
        glBufferData(GL_ARRAY_BUFFER, textureCoordinatesBuffer, GL_STATIC_DRAW);
    }

    public static class Character extends BasicShape {
        public void setCharacter(char character) {
            textureCoordinates = StringTexture.getCharacterCordinates(character);
        }
    }

    public static class Text {
        private float left, top, right, bottom;
        LList<Character> characters;
        private boolean disabled;

        private Text() {
            characters = new LList<>();
        }

        public void setCoordinates(float left, float top, float right, float bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        public void setText(String text) {
            char[] chars = text.toCharArray();
            float charWidth = (right - left) / chars.length;
            characters.removeAll(); // todo reuse characters
            for (int i = 0; i < chars.length; i++) {
                float offset = left + i * charWidth;
                Character character = new Character();
                character.setCoordinates(offset, top, offset + charWidth, bottom);
                character.setCharacter(chars[i]);
                characters.addTail(character);
            }
        }

        public void disable() {
            disabled = true;
        }

        public void enable() {
            disabled = false;
        }
    }
}