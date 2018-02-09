package camera;

import control.KeyButton;
import control.KeyControl;
import engine.Engine;
import geometry.CoordinateI3;
import org.lwjgl.system.MemoryUtil;
import util.MathAngles;
import util.MathNumbers;
import util.SimpleMatrix4f;
import util.intersection.IntersectionPicker;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Camera implements IntersectionPicker.Picker {
    private static final float FIELD_OF_VIEW = MathAngles.toRadians(60);
    private static final float PAN_WEIGHT = 1, ROTATE_WEIGHT = 1, ROTATE_Z_WEIGHT = 1;

    private float x, y, z;
    private float dx, dy, dz;
    private float theta, thetaZ;
    private Follow follow;

    private static final float TRAIL_DISTANCE_MIN = 3, TRAIL_DISTANCE_MAX = 60, TRAIL_DISTANCE_SPEED = .5f;
    private static final float TRAIL_VERT_MIN = 0, TRAIL_VERT_MAX = 25, TRAIL_VERT_SPEED = .2f;
    private float trailDistance, trailVert;

    private int projectionMatrixLoc, viewMatrixLoc, viewPositionLoc, antialiasLoc;
    private FloatBuffer viewMatrixBuffer;
    private int antialiasValue;

    public Camera(int renderProgramId) {
        x = 32 * Engine.SCALE;
        y = 16 * Engine.SCALE;
        theta = MathAngles.PI;

        trailDistance = 6f;
        trailVert = 1f;

        projectionMatrixLoc = glGetUniformLocation(renderProgramId, "projection");
        setupProjectionMatrix();

        viewMatrixLoc = glGetUniformLocation(renderProgramId, "view");
        viewMatrixBuffer = MemoryUtil.memAllocFloat(16);
        setViewMatrix(1);

        viewPositionLoc = glGetUniformLocation(renderProgramId, "viewPosition");

        antialiasLoc = glGetUniformLocation(renderProgramId, "antialias");
        setantialiasMode(antialiasValue = 1);
    }

    public void update(KeyControl keyControl) {
        trail(keyControl);
        if (follow.isFollowZoom()) {
            exactFollow();
            setViewMatrix(2);
        } else {
            follow();
            setViewMatrix(1);
        }

        setViewPosition();

        if (keyControl.isKeyPressed(KeyButton.KEY_0))
            setantialiasMode(antialiasValue = 1 - antialiasValue);
    }

    private void trail(KeyControl keyControl) {
        if (keyControl.isKeyDown(KeyButton.KEY_R))
            trailVert = MathNumbers.min(trailVert + TRAIL_VERT_SPEED, TRAIL_VERT_MAX);
        if (keyControl.isKeyDown(KeyButton.KEY_F))
            trailVert = MathNumbers.max(trailVert - TRAIL_VERT_SPEED, TRAIL_VERT_MIN);

        if (keyControl.isKeyDown(KeyButton.KEY_Z))
            trailDistance = MathNumbers.min(trailDistance + TRAIL_DISTANCE_SPEED, TRAIL_DISTANCE_MAX);
        if (keyControl.isKeyDown(KeyButton.KEY_X))
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
    }

    private void moveTo(float toX, float toY, float toZ) {
        x += (toX - x) * PAN_WEIGHT;
        y += (toY - y) * PAN_WEIGHT;
        z += (toZ - z) * PAN_WEIGHT;
    }

    private void lookAt(float lookX, float lookY, float lookZ) {
        dx = lookX - x;
        dy = lookY - y;
        dz = lookZ - z;
        float goalTheta = (float) Math.atan2(-dx, -dz);
        float goalThetaZ = (float) Math.atan2(dy, MathNumbers.magnitude(dx, dz));
        rotateTo(goalTheta, goalThetaZ);
    }

    private void rotateTo(float toTheta, float toThetaZ) {
        if (toTheta - theta > MathAngles.PI)
            theta += MathAngles.PI2;
        else if (toTheta - theta < -MathAngles.PI)
            theta -= MathAngles.PI2;

        theta += (toTheta - theta) * ROTATE_WEIGHT;
        thetaZ += (toThetaZ - thetaZ) * ROTATE_Z_WEIGHT;
    }

    private void exactFollow() {
        x = follow.getFollowX();
        y = follow.getFollowY();
        z = follow.getFollowZ();
        theta = follow.getFollowTheta();
        thetaZ = follow.getFollowThetaZ();

        float[] followNorm = follow.getFollowNorm();
        dx = followNorm[0];
        dy = followNorm[1];
        dz = followNorm[2];
    }

    private void setupProjectionMatrix() {
        SimpleMatrix4f projectionMatrix = SimpleMatrix4f.perspective(FIELD_OF_VIEW, 1, 1, 1000);
        FloatBuffer projectionMatrixBuffer = MemoryUtil.memAllocFloat(16);
        projectionMatrix.toBuffer(projectionMatrixBuffer);
        glUniformMatrix4fv(projectionMatrixLoc, false, projectionMatrixBuffer);
        MemoryUtil.memFree(projectionMatrixBuffer);
    }

    private void setViewMatrix(float scale) {
        SimpleMatrix4f.invModelMatrix(x, y, z, theta, thetaZ, scale).toBuffer(viewMatrixBuffer);
        glUniformMatrix4fv(viewMatrixLoc, false, viewMatrixBuffer);
    }

    private void setViewPosition() {
        glUniform3f(viewPositionLoc, x, y, z);
    }

    private void setantialiasMode(int antiValue) {
        glUniform1i(antialiasLoc, antiValue);
    } // todo capitalize A

    public CoordinateI3 getWorldCoordinate() {
        return new CoordinateI3((int) x, (int) -z, (int) y);
    }

    public void setFollow(Follow follow) {
        this.follow = follow;
    }

    @Override
    public float getWorldX() {
        return x;
    }

    @Override
    public float getWorldY() {
        return -z;
    }

    @Override
    public float getWorldZ() {
        return y;
    }

    @Override
    public float getWorldDirX() {
        return dx;
    }

    @Override
    public float getWorldDirY() {
        return -dz;
    }

    @Override
    public float getWorldDirZ() {
        return dy;
    }

    @Override
    public float getPickOffset() {
        return MathNumbers.magnitude(trailDistance, trailVert); // todo cache value
    }
}