package shape;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

class Texture {
    private int width, height;
    private int textureId;

    Texture(BufferedImage image) {
        int[] imageARGB = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        width = image.getWidth();
        height = image.getHeight();
        ByteBuffer byteBuffer = createByteBuffer(imageARGB, width, height);
        textureId = createTexture(byteBuffer, width, height);
    }

    private static ByteBuffer createByteBuffer(int[] imageARGB, int width, int height) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(width * height * 4);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = imageARGB[y * width + x];
                byteBuffer.put((byte) ((argb >> 16) & 0xFF));
                byteBuffer.put((byte) ((argb >> 8) & 0xFF));
                byteBuffer.put((byte) (argb & 0xFF));
                byteBuffer.put((byte) ((argb >> 24) & 0xFF));
            }
        }
        byteBuffer.flip();

        return byteBuffer;
    }

    private static int createTexture(ByteBuffer byteBuffer, int width, int height) {
        int textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuffer);
        return textureId;
    }
}