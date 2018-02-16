package model.segment;

public class SegmentEditable extends Segment {
    public SegmentEditable() {
        super(new float[0]);
    }

    public SegmentEditable(SegmentData segmentData) {
        super(segmentData);
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