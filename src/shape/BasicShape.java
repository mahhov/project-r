package shape;

import util.math.MathArrays;

class BasicShape {
    float[] vertices, colors, textureCoordinates;
    private boolean disabled;

    public void setCoordinates(float left, float top, float right, float bottom) {
        vertices = new float[] {left, top, left, bottom, right, bottom, right, top, left, top, right, bottom};
    }

    public void setColor(float[] color) {
        colors = MathArrays.repeatArray(color, 6);
    }

    public void disable() {
        disabled = true;
    }

    public void enable() {
        disabled = false;
    }

    boolean enabled() {
        return !disabled;
    }
}