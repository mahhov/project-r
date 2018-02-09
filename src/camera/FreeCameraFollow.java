package camera;

import control.KeyButton;
import control.KeyControl;
import util.MathAngles;
import util.MathNumbers;

public class FreeCameraFollow implements Follow {
    private static final float SPEED = .1f;
    private float theta, thetaZ;

    public void update(KeyControl keyControl) {
        if (keyControl.isKeyDown(KeyButton.KEY_W)) {
            thetaZ -= SPEED;
            thetaZ = MathNumbers.minMax(thetaZ, -MathAngles.MAX_THETA_Z, MathAngles.MAX_THETA_Z);
        }
        if (keyControl.isKeyDown(KeyButton.KEY_S)) {
            thetaZ += SPEED;
            thetaZ = MathNumbers.minMax(thetaZ, -MathAngles.MAX_THETA_Z, MathAngles.MAX_THETA_Z);
        }
        if (keyControl.isKeyDown(KeyButton.KEY_A))
            theta -= SPEED;
        if (keyControl.isKeyDown(KeyButton.KEY_D))
            theta += SPEED;

    }

    @Override
    public float getFollowX() {
        return 0;
    }

    @Override
    public float getFollowY() {
        return 0;
    }

    @Override
    public float getFollowZ() {
        return 0;
    }

    @Override
    public float getFollowTheta() {
        return theta;
    }

    @Override
    public float getFollowThetaZ() {
        return thetaZ;
    }

    @Override
    public float[] getFollowNorm() {
        float[] norm = new float[2];
        norm[0] = -MathAngles.sin(theta);
        norm[1] = MathAngles.cos(theta);

        float thetaZCos = MathAngles.cos(thetaZ);
        return new float[] {norm[0] * thetaZCos, MathAngles.sin(thetaZ), -norm[1] * thetaZCos};
    }

    @Override
    public boolean isFollowZoom() {
        return false;
    }
}