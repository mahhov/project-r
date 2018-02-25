package model;

import model.segment.Segment;

class Animation {
    private static final int ANIMATION_COUNT = 2;
    private int prevFrameIndex, frameIndex, lapsedTime, totalTime[];
    private Frame[][] frames;
    private Frame[] frame;

    Animation(int segmentCount) {
        totalTime = new int[ANIMATION_COUNT];
        frames = new Frame[ANIMATION_COUNT][segmentCount];
        for (int i = 0; i < ANIMATION_COUNT; i++) {
            totalTime[i] = 10;
            for (int j = 0; j < segmentCount; j++)
                frames[i][j] = new Frame();
        }
        frames[1][1].z = 1;

        frame = new Frame[segmentCount];
        for (int i = 0; i < segmentCount; i++)
            frame[i] = new Frame();

        frameIndex = 1;
        lapsedTime = totalTime[0];
    }

    void walk() {
        if (--lapsedTime == 0) {
            prevFrameIndex = frameIndex;
            if (++frameIndex == frames.length)
                frameIndex = 0;
            lapsedTime = totalTime[prevFrameIndex];
        }

        averageFrames(frames[prevFrameIndex], frames[frameIndex], 1f * lapsedTime / totalTime[prevFrameIndex]);
    }

    private void averageFrames(Frame[] frame1, Frame[] frame2, float weight) {
        float invWeight = 1 - weight;
        for (int i = 0; i < frame.length; i++) {
            frame[i].x = frame1[i].x * weight + frame2[i].x * invWeight;
            frame[i].y = frame1[i].y * weight + frame2[i].y * invWeight;
            frame[i].z = frame1[i].z * weight + frame2[i].z * invWeight;
            frame[i].theta = frame1[i].theta * weight + frame2[i].theta * invWeight;
        }
    }

    void apply(Segment[] segments) {
        for (int i = 0; i < segments.length; i++)
            segments[i].setAnimation(frame[i].x, frame[i].y, frame[i].z, frame[i].theta);
    }

    private static class Frame {
        private float x, y, z, theta;
    }
}