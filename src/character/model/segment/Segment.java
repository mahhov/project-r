package character.model.segment;

import shape.CubeInstancedFaces;
import util.math.MathAngles;

public class Segment {
    private Segment parent;
    float scaleX, scaleY, scaleZ, color[];
    private CubeInstancedFaces cubeInstancedFaces;
    Transformation transformation;
    private Transformation compositeTransformation;
    boolean stale;

    public Segment(float[] color) {
        this.color = color;
        transformation = new Transformation();
        compositeTransformation = new Transformation();
        stale = true;
    }

    public Segment(SegmentData segmentData) {
        this(segmentData.color);
        scaleX = segmentData.scaleX;
        scaleY = segmentData.scaleY;
        scaleZ = segmentData.scaleZ;
        transformation.x = segmentData.transformationX;
        transformation.y = segmentData.transformationY;
        transformation.z = segmentData.transformationZ;
        transformation.theta = segmentData.transformationTheta;
    }

    public void init(Segment parent, CubeInstancedFaces cubeInstancedFaces) {
        this.parent = parent;
        this.cubeInstancedFaces = cubeInstancedFaces;
    }

    public void setScale(float scale) {
        scaleX = scaleY = scaleZ = scale;
    }

    public void setScale(float scaleX, float scaleY, float scaleZ) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }

    public void setTranslation(float x, float y, float z) {
        transformation.x = x;
        transformation.y = y;
        transformation.z = z;
        stale = true;
    }

    public void setRotation(float theta) {
        transformation.theta = theta;
        stale = true;
    }

    private Transformation getCompositeTransformation() {
        if (parent == null) {
            compositeTransformation = transformation;
            MathAngles.norm(compositeTransformation.theta, compositeTransformation.norm);
            return compositeTransformation;
        }

        if (isStale()) {
            Transformation parentTransformation = parent.getCompositeTransformation();
            compositeTransformation.x = parentTransformation.x + parentTransformation.norm[0] * transformation.y + parentTransformation.norm[1] * transformation.x;
            compositeTransformation.y = parentTransformation.y + parentTransformation.norm[1] * transformation.y - parentTransformation.norm[0] * transformation.x;
            compositeTransformation.z = parentTransformation.z + transformation.z;
            compositeTransformation.theta = parentTransformation.theta + transformation.theta;
            MathAngles.norm(compositeTransformation.theta, compositeTransformation.norm);
            stale = false;
        }

        return compositeTransformation;
    }

    private boolean isStale() {
        return stale || (parent != null && parent.isStale());
    }

    public void draw() {
        getCompositeTransformation();
        cubeInstancedFaces.add(compositeTransformation.x, compositeTransformation.z, -compositeTransformation.y, compositeTransformation.theta, 0, scaleX, scaleZ, scaleY, color, false);
    }

    class Transformation {
        float x, y, z, theta;
        private float[] norm;

        private Transformation() {
            norm = new float[2];
        }
    }

    public Segment getParent() {
        return parent;
    }

    public SegmentData getSegmentData() {
        SegmentData segmentData = new SegmentData();

        segmentData.scaleX = scaleX;
        segmentData.scaleY = scaleY;
        segmentData.scaleZ = scaleZ;
        segmentData.color = color;
        segmentData.transformationX = transformation.x;
        segmentData.transformationY = transformation.y;
        segmentData.transformationZ = transformation.z;
        segmentData.transformationTheta = transformation.theta;

        return segmentData;
    }
}