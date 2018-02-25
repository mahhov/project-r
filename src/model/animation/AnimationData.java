package model.animation;

import java.io.Serializable;

public class AnimationData implements Serializable {
    int segmentCount;
    int[] totalTime;
    Frame[][] frames;

    public AnimationData(int animationCount, int segmentCount) {
        this.segmentCount = segmentCount;
        totalTime = new int[segmentCount];
        frames = new Frame[animationCount][segmentCount];
        for (int i = 0; i < animationCount; i++) {
            totalTime[i] = 10;
            for (int j = 0; j < segmentCount; j++)
                frames[i][j] = new Frame();
        }
        frames[1][1].z = 1;
    }
}