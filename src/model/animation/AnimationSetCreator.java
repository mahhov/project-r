package model.animation;

public class AnimationSetCreator extends AnimationSetData {
    public AnimationSetCreator(int segmentCount) {
        for (int i = 0; i < AnimationSet.ANIMATION_TYPE_VALUES.length; i++)
            animationDatas[i] = new AnimationData(1, segmentCount);
    }

    public void setAnimationData(AnimationSet.AnimationType animationType, AnimationData animationData) {
        animationDatas[animationType.value] = animationData;
    }
}