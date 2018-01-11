package shape;

import java.awt.*;
import java.awt.image.BufferedImage;

public class StringTexture extends Texture {
    private static final int CHAR_SIZE = 12;
    private static final StringTexture ALPHABET = new StringTexture("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

    private StringTexture(String string) {
        super(characterImage(string.toCharArray()));
    }

    private static BufferedImage characterImage(char[] chars) {
        BufferedImage stringImage = new BufferedImage(chars.length * CHAR_SIZE, CHAR_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) stringImage.getGraphics();
        g.setColor(Color.WHITE);
        for (int i = 0; i < 26; i++)
            g.drawString(chars[i] + "", 1 + i * CHAR_SIZE, 10);
        return stringImage;
    }

    static float[] getCharacterCordinates(char c) {
        int i = c - 'a';
        float left = i / 26f;
        float right = left + 1 / 26f;
        return new float[] {left, 0, left, 1, right, 1, right, 0, left, 0, right, 1};
    }
}