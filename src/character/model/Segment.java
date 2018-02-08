package character.model;

import shape.CubeInstancedFaces;
import util.MathAngles;
import util.SimpleMatrix4f;

class Segment {
    private Segment parent;
    private SimpleMatrix4f modelMatrix;
    private float size, color[];
    private CubeInstancedFaces cubeInstancedFaces;
    private Transformation transformation, compositeTransformation;
    private boolean stale;

    Segment(Segment parent, float size, float[] color, CubeInstancedFaces cubeInstancedFaces) {
        this.parent = parent;
        this.size = size;
        this.color = color;
        this.cubeInstancedFaces = cubeInstancedFaces;
        transformation = new Transformation();
        compositeTransformation = new Transformation();
        stale = true;
    }

    void setTranslation(float x, float y, float z) {
        transformation.x = x;
        transformation.y = y;
        transformation.z = z;
        stale = true;
    }

    void setRotation(float theta) {
        transformation.theta = theta;
        stale = true;
    }

    private Transformation getCompositeTransformation() {
        if (parent == null)
            return compositeTransformation = transformation;

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

    void draw() {
        getCompositeTransformation();
        cubeInstancedFaces.add(compositeTransformation.x, compositeTransformation.z, -compositeTransformation.y, compositeTransformation.theta, 0, size, color);
    }

    private class Transformation {
        private float x, y, z, theta;
        private float[] norm;

        private Transformation() {
            norm = new float[3];
        }
    }
}