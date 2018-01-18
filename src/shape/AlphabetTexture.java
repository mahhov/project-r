package shape;

import java.awt.*;
import java.awt.image.BufferedImage;

class AlphabetTexture extends Texture {
    private static final int CHAR_SIZE, ASCII_COUNT = 128;
    static final float CHAR_BUFFER, CHAR_WIDTHS[];
    private static final float INV_ASCII_COUNT = 1f / ASCII_COUNT;

    static {
        BufferedImage stringImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics g = stringImage.getGraphics();
        FontMetrics fm = g.getFontMetrics();

        CHAR_SIZE = fm.getMaxAscent() + fm.getMaxDescent();
        int[] charWidthsInt = fm.getWidths();
        CHAR_WIDTHS = new float[ASCII_COUNT];
        CHAR_BUFFER = 2f / CHAR_SIZE;
        for (int i = 0; i < ASCII_COUNT; i++)
            CHAR_WIDTHS[i] = 1f * charWidthsInt[i] / CHAR_SIZE + CHAR_BUFFER;
    }

    private static final AlphabetTexture ALPHABET_TEXTURE = new AlphabetTexture();

    private AlphabetTexture() {
        super(characterImage());
    }

    private static BufferedImage characterImage() {
        BufferedImage stringImage = new BufferedImage(ASCII_COUNT * CHAR_SIZE, CHAR_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) stringImage.getGraphics();
        g.setColor(Color.WHITE);
        for (int i = 0; i < ASCII_COUNT; i++)
            g.drawString(((char) i) + "", 1 + i * CHAR_SIZE, 10);
        return stringImage;
    }

    static float[] getCharacterCoordinates(char c) {
        float left = ((int) c) * INV_ASCII_COUNT;
        float right = left + INV_ASCII_COUNT * CHAR_WIDTHS[c];
        return new float[] {left, 0, left, 1, right, 1, right, 0, left, 0, right, 1};
    }
}