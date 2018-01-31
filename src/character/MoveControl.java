package character;

public class MoveControl {
    public float dx, dy, speed; // speed between 0 and 1
    public float dz; // dz to be target dz, will be clampbed by JET_ACC
    public boolean jump, glide, boost;
    public float theta, thetaZ;
}