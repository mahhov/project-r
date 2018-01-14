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
    private FloatBuffer verticesBuffer, textureCoordinatesBuffer, colorsBuffer;
    private int verticesVboId, textureCoordinatesVboId, colorsVboId;
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
        colorsBuffer = MemoryUtil.memAllocFloat(maxNumVertices * 4);
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

        colorsVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, colorsVboId);
        glVertexAttribPointer(2, 4, GL_FLOAT, false, 0, 0); // todo make instnaced
        glEnableVertexAttribArray(2);
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
        colorsBuffer.clear();
        numCharacters = 0;
        for (Text text : texts)
            if (!text.disabled) {
                numCharacters += text.characters.size();
                for (Character character : text.characters) {
                    verticesBuffer.put(character.vertices);
                    textureCoordinatesBuffer.put(character.textureCoordinates);
                    colorsBuffer.put(character.colors);
                }
            }
        verticesBuffer.flip();
        textureCoordinatesBuffer.flip();
        colorsBuffer.flip();
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

        glBindBuffer(GL_ARRAY_BUFFER, colorsVboId);
        glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);
    }

    public static class Character extends BasicShape {
        public void setCharacter(char character) {
            textureCoordinates = AlphabetTexture.getCharacterCordinates(character);
        }
    }

    public static class Text {
        private static final float[] DEFAULT_COLOR = new float[] {1, 1, 1, 1};
        private float left, top, right, bottom;
        private boolean autoWidth;
        LList<Character> characters;
        private boolean disabled;
        private float[] color;

        private Text() {
            characters = new LList<>();
            color = DEFAULT_COLOR;
        }

        public void setCoordinates(float left, float top, float right, float bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            autoWidth = false;
        }

        public void setCoordinates(float left, float top, float bottom) {
            this.left = left;
            this.top = top;
            this.bottom = bottom;
            autoWidth = true;
        }

        public void setText(String text) {
            char[] chars = text.toCharArray();
            float charWidth = autoWidth ? top - bottom : (right - left) / chars.length;
            characters.removeAll(); // todo reuse characters
            for (int i = 0; i < chars.length; i++) {
                float offset = left + i * charWidth;
                Character character = new Character();
                character.setCoordinates(offset, top, offset + charWidth, bottom);
                character.setCharacter(chars[i]);
                character.setColor(color);
                characters.addTail(character);
            }
        }

        public void setColor(float[] color) {
            this.color = color == null ? DEFAULT_COLOR : color;
        }

        public void disable() {
            disabled = true;
        }

        public void enable() {
            disabled = false;
        }
    }
}