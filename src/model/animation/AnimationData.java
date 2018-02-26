package model.animation;

import java.io.Serializable;

public class AnimationData implements Serializable {
    int[] totalTime;
    Frame[][] frames;

    public AnimationData(int animationCount, int segmentCount) {
        totalTime = new int[animationCount];
        frames = new Frame[animationCount][segmentCount];
        for (int i = 0; i < animationCount; i++) {
            totalTime[i] = 10;
            for (int j = 0; j < segmentCount; j++)
                frames[i][j] = new Frame();
        }
    }
}