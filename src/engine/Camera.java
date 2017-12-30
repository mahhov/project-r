package engine;

import geometry.CoordinateI3;
import org.lwjgl.system.MemoryUtil;
import util.MathAngles;
import util.MathNumbers;
import util.lwjgl.SimpleMatrix4f;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

class Camera {
    private static final float FIELD_OF_VIEW = MathAngles.toRadians(60);
    private static final float MOVE_SEED = 3f, ROTATE_SPEED = .03f;

    private float x, y, z;
    private float theta, thetaZ;

    private int projectionMatrixLoc, viewMatrixLoc;
    private SimpleMatrix4f thetaMatrix, thetaZMatrix, moveMatrix, viewMatrix;
    private FloatBuffer viewMatrixBuffer;

    Camera(int programId) {
        x = 32 * Engine.SCALE;
        y = 16 * Engine.SCALE;
        theta = MathAngles.PI;

        projectionMatrixLoc = glGetUniformLocation(programId, "projection");
        setupProjectionMatrix();

        viewMatrixLoc = glGetUniformLocation(programId, "view");
        viewMatrixBuffer = MemoryUtil.memAllocFloat(16);
        setViewMatrix();
    }

    void update(Controller controller) {
        if (controller.isKeyDown(Controller.KEY_W)) {
            x -= MOVE_SEED * Math.sin(theta);
            z -= MOVE_SEED * Math.cos(theta);
        }
        if (controller.isKeyDown(Controller.KEY_S)) {
            x += MOVE_SEED * Math.sin(theta);
            z += MOVE_SEED * Math.cos(theta);
        }
        if (controller.isKeyDown(Controller.KEY_A)) {
            x -= MOVE_SEED * Math.cos(theta);
            z += MOVE_SEED * Math.sin(theta);
        }
        if (controller.isKeyDown(Controller.KEY_D)) {
            x += MOVE_SEED * Math.cos(theta);
            z -= MOVE_SEED * Math.sin(theta);
        }

        if (controller.isKeyDown(Controller.KEY_SHIFT))
            y -= MOVE_SEED;
        if (controller.isKeyDown(Controller.KEY_SPACE))
            y += MOVE_SEED;
        if (controller.isKeyDown(Controller.KEY_Q))
            theta += ROTATE_SPEED;
        if (controller.isKeyDown(Controller.KEY_E))
            theta -= ROTATE_SPEED;
        if (controller.isKeyDown(Controller.KEY_R))
            thetaZ = MathNumbers.min(thetaZ + ROTATE_SPEED, MathAngles.PI / 2);
        if (controller.isKeyDown(Controller.KEY_F))
            thetaZ = MathNumbers.max(thetaZ - ROTATE_SPEED, -MathAngles.PI / 2);

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
        thetaZMatrix = SimpleMatrix4f.rotate(-thetaZ, 1, 0, 0);
        thetaMatrix = SimpleMatrix4f.rotate(-theta, 0, 1, 0);
        moveMatrix = SimpleMatrix4f.translate(-x, -y, -z);
        viewMatrix = thetaZMatrix.multiply(thetaMatrix).multiply(moveMatrix);
        viewMatrix.toBuffer(viewMatrixBuffer);
        glUniformMatrix4fv(viewMatrixLoc, false, viewMatrixBuffer);
    }

    CoordinateI3 getIntCoordinate() {
        return new CoordinateI3((int) x, (int) z, (int) y);
    }
}