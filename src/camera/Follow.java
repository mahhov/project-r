package camera;

public interface Follow {
    float getFollowX();

    float getFollowY();

    float getFollowZ();

    float getFollowTheta();

    float getFollowThetaZ();

    float[] getFollowNorm();
    
    boolean isFollowZoom();
}