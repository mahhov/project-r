package model.animation;

import java.io.Serializable;

class AnimationData implements Serializable {
    int[] totalTime;
    Frame[][] frames;

    AnimationData(int frameCount, int segmentCount) {
        totalTime = new int[frameCount];
        frames = new Frame[frameCount][segmentCount];
        for (int i = 0; i < frameCount; i++) {
            totalTime[i] = 10;
            for (int j = 0; j < segmentCount; j++)
                frames[i][j] = new Frame();
        }
    }
}