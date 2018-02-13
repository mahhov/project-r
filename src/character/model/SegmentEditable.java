package character.model;

import shape.CubeInstancedFaces;

public class SegmentEditable extends Segment {
    public SegmentEditable(SegmentEditable parent, float[] color, CubeInstancedFaces cubeInstancedFaces) {
        super(parent, color, cubeInstancedFaces);
    }

    public void addScale(float scaleX, float scaleY, float scaleZ) {
        this.scaleX += scaleX;
        this.scaleY += scaleY;
        this.scaleZ += scaleZ;
    }

    public void addTranslation(float x, float y, float z) {
        transformation.x += x;
        transformation.y += y;
        transformation.z += z;
        stale = true;
    }

    public void addRotation(float theta) {
        transformation.theta += theta;
        stale = true;
    }

    public void setColor(float[] color) {
        this.color = color;
    }
}