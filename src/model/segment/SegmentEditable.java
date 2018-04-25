package model.segment;

public class SegmentEditable extends Segment {
    public SegmentEditable() {
        super(new float[0]);
    }
    
    public void setScale(SegmentEditable relative) {
        scaleX = relative.scaleX;
        scaleY = relative.scaleY;
        scaleZ = relative.scaleZ;
    }

    // todo: leftOf and relative positioning should consider that parents of the 2 segments may be different resulting in different origin
    public SegmentEditable leftOf(SegmentEditable relative) {
        transformation.x = relative.transformation.x - relative.scaleX / 2 - scaleX / 2;
        transformation.y = relative.transformation.y;
        transformation.z = relative.transformation.z;
        return this;
    }

    public SegmentEditable rightOf(SegmentEditable relative) {
        transformation.x = relative.transformation.x + relative.scaleX / 2 + scaleX / 2;
        transformation.y = relative.transformation.y;
        transformation.z = relative.transformation.z;
        return this;
    }

    public SegmentEditable backOf(SegmentEditable relative) {
        transformation.x = relative.transformation.x;
        transformation.y = relative.transformation.y - relative.scaleY / 2 - scaleY / 2;
        transformation.z = relative.transformation.z;
        return this;
    }

    public SegmentEditable frontOf(SegmentEditable relative) {
        transformation.x = relative.transformation.x;
        transformation.y = relative.transformation.y + relative.scaleY / 2 + scaleY / 2;
        transformation.z = relative.transformation.z;
        return this;
    }

    public SegmentEditable bottomOf(SegmentEditable relative) {
        transformation.x = relative.transformation.x;
        transformation.y = relative.transformation.y;
        transformation.z = relative.transformation.z - relative.scaleZ / 2 - scaleZ / 2;
        return this;
    }

    public SegmentEditable topOf(SegmentEditable relative) {
        transformation.x = relative.transformation.x;
        transformation.y = relative.transformation.y;
        transformation.z = relative.transformation.z + relative.scaleZ / 2 + scaleZ / 2;
        return this;
    }

    public SegmentEditable leftAlign(float relative) {
        transformation.x = relative + scaleX / 2;
        return this;
    }

    public SegmentEditable rightAlign(float relative) {
        transformation.x = relative - scaleX / 2;
        return this;
    }

    public SegmentEditable backAlign(float relative) {
        transformation.y = relative + scaleY / 2;
        return this;
    }

    public SegmentEditable frontAlign(float relative) {
        transformation.y = relative - scaleY / 2;
        return this;
    }

    public SegmentEditable bottomAlign(float relative) {
        transformation.z = relative + scaleZ / 2;
        return this;
    }

    public SegmentEditable topAlign(float relative) {
        transformation.z = relative - scaleZ / 2;
        return this;
    }

    public float left() {
        return transformation.x - scaleX / 2;
    }

    public float right() {
        return transformation.x + scaleX / 2;
    }

    public float back() {
        return transformation.y - scaleY / 2;
    }

    public float front() {
        return transformation.y + scaleY / 2;
    }

    public float bottom() {
        return transformation.z - scaleZ / 2;
    }

    public float top() {
        return transformation.z + scaleZ / 2;
    }

    public SegmentEditable translate(float x, float y, float z) {
        transformation.x += x;
        transformation.y += y;
        transformation.z += z;
        return this;
    }

    public void setColor(float[] color) {
        this.color = color;
    }
}
