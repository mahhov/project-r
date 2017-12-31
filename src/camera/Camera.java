package camera;

import engine.Engine;
import geometry.CoordinateI3;
import org.lwjgl.system.MemoryUtil;
import util.MathAngles;
import util.lwjgl.SimpleMatrix4f;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class Camera {
    private static final float FIELD_OF_VIEW = MathAngles.toRadians(60);
    private static final double PAN_WEIGHT = .1, ROTATE_WEIGHT = .1;

    private float x, y, z;
    private float theta, thetaZ;
    private Follow follow;

    private int projectionMatrixLoc, viewMatrixLoc;
    private FloatBuffer viewMatrixBuffer;

    public Camera(int programId) {
        x = 32 * Engine.SCALE;
        y = 16 * Engine.SCALE;
        theta = MathAngles.PI;

        projectionMatrixLoc = glGetUniformLocation(programId, "projection");
        setupProjectionMatrix();

        viewMatrixLoc = glGetUniformLocation(programId, "view");
        viewMatrixBuffer = MemoryUtil.memAllocFloat(16);
        setViewMatrix();
    }

    public void update() {
        x += (follow.getFollowX() - x) * PAN_WEIGHT;
        y += (follow.getFollowY() - y) * PAN_WEIGHT;
        z += (follow.getFollowZ() - z) * PAN_WEIGHT;
        theta += (follow.getFollowTheta() - theta) * ROTATE_WEIGHT;
        thetaZ += (follow.getFollowThetaZ() - thetaZ) * ROTATE_WEIGHT;

        setViewMatrix();
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