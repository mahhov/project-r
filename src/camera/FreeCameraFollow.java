package camera;

import control.MousePosControl;
import util.math.MathAngles;
import util.math.MathNumbers;

public class FreeCameraFollow implements Follow {
    private static final float ROTATE_SPEED_MOUSE = .008f;
    private static final float ROTATE_SPEED_KEY = .1f;
    private float theta, thetaZ;

    public void update(MousePosControl mousePosControl) {
        theta -= ROTATE_SPEED_MOUSE * mousePosControl.getMoveX();
        thetaZ -= ROTATE_SPEED_MOUSE * mousePosControl.getMoveY();

        thetaZ = MathNumbers.minMax(thetaZ, -MathAngles.MAX_THETA_Z, MathAngles.MAX_THETA_Z);
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