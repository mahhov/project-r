package camera;

import control.KeyControl;
import engine.Engine;
import geometry.CoordinateI3;
import org.lwjgl.system.MemoryUtil;
import util.MathAngles;
import util.MathNumbers;
import util.SimpleMatrix4f;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class Camera {
    private static final float FIELD_OF_VIEW = MathAngles.toRadians(60);
    private static final float PAN_WEIGHT = .2f, ROTATE_WEIGHT = .5f, ROTATE_Z_WEIGHT = .2f;

    private float x, y, z;
    private float theta, thetaZ;
    private Follow follow;

    private static final float TRAIL_DISTANCE_MIN = 5, TRAIL_DISTANCE_MAX = 60, TRAIL_DISTANCE_SPEED = 2.5f;
    private static final float TRAIL_VERT_MIN = 0, TRAIL_VERT_MAX = 25, TRAIL_VERT_SPEED = 1;
    private float trailDistance, trailVert;

    private int projectionMatrixLoc, viewMatrixLoc;
    private FloatBuffer viewMatrixBuffer;

    public Camera(int programId) {
        x = 32 * Engine.SCALE;
        y = 16 * Engine.SCALE;
        theta = MathAngles.PI;

        trailDistance = 17.5f;
        trailVert = 8.5f;

        projectionMatrixLoc = glGetUniformLocation(programId, "projection");
        setupProjectionMatrix();

        viewMatrixLoc = glGetUniformLocation(programId, "view");
        viewMatrixBuffer = MemoryUtil.memAllocFloat(16);
        setViewMatrix();
    }

    public void update(KeyControl keyControl) {
        trail(keyControl);
        follow();
    }

    private void trail(KeyControl keyControl) {
        if (keyControl.isKeyDown(KeyControl.KEY_R))
            trailVert = MathNumbers.min(trailVert + TRAIL_VERT_SPEED, TRAIL_VERT_MAX);
        if (keyControl.isKeyDown(KeyControl.KEY_F))
            trailVert = MathNumbers.max(trailVert - TRAIL_VERT_SPEED, TRAIL_VERT_MIN);

        if (keyControl.isKeyDown(KeyControl.KEY_Z))
            trailDistance = MathNumbers.min(trailDistance + TRAIL_DISTANCE_SPEED, TRAIL_DISTANCE_MAX);
        if (keyControl.isKeyDown(KeyControl.KEY_X))
            trailDistance = MathNumbers.max(trailDistance - TRAIL_DISTANCE_SPEED, TRAIL_DISTANCE_MIN);
    }

    private void follow() {
        float theta = follow.getFollowTheta(), thetaZ = follow.getFollowThetaZ();
        float followY = follow.getFollowY() + trailVert;

        float goalX = follow.getFollowX() + MathAngles.sin(theta) * MathAngles.cos(thetaZ) * trailDistance;
        float goalY = followY - MathAngles.sin(thetaZ) * trailDistance;
        float goalZ = follow.getFollowZ() + MathAngles.cos(theta) * MathAngles.cos(thetaZ) * trailDistance;

        moveTo(goalX, goalY, goalZ);
        lookAt(follow.getFollowX(), followY, follow.getFollowZ());

        //        this.theta = theta;
        //        this.thetaZ = thetaZ;

        setViewMatrix();
    }

    private void moveTo(float toX, float toY, float toZ) {
        x += (toX - x) * PAN_WEIGHT;
        y += (toY - y) * PAN_WEIGHT;
        z += (toZ - z) * PAN_WEIGHT;
    }

    private void lookAt(float lookX, float lookY, float lookZ) {
        float dx = lookX - x;
        float dy = lookY - y;
        float dz = lookZ - z;
        float goalTheta = (float) Math.atan2(-dx, -dz);
        float goalThetaZ = (float) Math.atan2(dy, MathNumbers.magnitude(dx, dz));
        rotateTo(goalTheta, goalThetaZ);
    }

    private void rotateTo(float toTheta, float toThetaZ) {
        theta += (toTheta - theta) * ROTATE_WEIGHT;
        thetaZ += (toThetaZ - thetaZ) * ROTATE_Z_WEIGHT;
    }

    private void setupProjectionMatrix() {
        SimpleMatrix4f projectionMatrix = SimpleMatrix4f.perspective(FIELD_OF_VIEW, 1, 1, 1000);
        FloatBuffer projectionMatrixBuffer = MemoryUtil.memAllocFloat(16);
        projectionMatrix.toBuffer(projectionMatrixBuffer);
        glUniformMatrix4fv(projectionMatrixLoc, false, projectionMatrixBuffer);
        MemoryUtil.memFree(projectionMatrixBuffer);
    }

    private void setViewMatrix() {
        SimpleMatrix4f.invModelMatrix(x, y, z, theta, thetaZ).toBuffer(viewMatrixBuffer);
        glUniformMatrix4fv(viewMatrixLoc, false, viewMatrixBuffer);
    }

    public CoordinateI3 getWorldCoordinate() {
        return new CoordinateI3((int) x, (int) -z, (int) y);
    }

    public void setFollow(Follow follow) {
        this.follow = follow;
    }
}