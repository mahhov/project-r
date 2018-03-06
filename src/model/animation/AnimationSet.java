package model.animation;

import model.segment.Segment;

public class AnimationSet {
    public enum AnimationType {
        STAND, WALK;

        int value;

        AnimationType() {
            value = ordinal();
        }
    }

    static AnimationType[] ANIMATION_TYPE_VALUES = AnimationType.values();

    private AnimationType currentAnimation;
    private Animation[] animations;

    public AnimationSet(AnimationSetData animationSetData) {
        currentAnimation = AnimationType.STAND;
        animations = new Animation[ANIMATION_TYPE_VALUES.length];
        for (int i = 0; i < ANIMATION_TYPE_VALUES.length; i++)
            animations[i] = new Animation(animationSetData.animationDatas[i]);
    }

    public void animate(AnimationType animationType) {
        if (currentAnimation != animationType) {
            currentAnimation = animationType;
            animations[currentAnimation.value].reset();
        } else
            animations[currentAnimation.value].progress();
    }
    
    public void animate() {
            animations[currentAnimation.value].progress();
    }

    public void apply(Segment[] segments) {
        animations[currentAnimation.value].apply(segments);
    }
}