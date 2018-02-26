package model.animation;

public class AnimationCreator extends AnimationData {
    public AnimationCreator(int animationCount, int segmentCount) {
        super(animationCount, segmentCount);
    }

    public void setTotalTime(int frameIndex, int totalTime) {
        this.totalTime[frameIndex] = totalTime;
    }

    public void setFrame(int frameIndex, int segmentIndex, float x, float y, float z, float theta) {
        frames[frameIndex][segmentIndex].x = x;
        frames[frameIndex][segmentIndex].y = y;
        frames[frameIndex][segmentIndex].z = z;
        frames[frameIndex][segmentIndex].theta = theta;
    }
}