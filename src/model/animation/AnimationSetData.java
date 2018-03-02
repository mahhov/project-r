package model.animation;

import java.io.Serializable;

public class AnimationSetData implements Serializable {
    AnimationData[] animationDatas;

    AnimationSetData() {
        animationDatas = new AnimationData[AnimationSet.ANIMATION_TYPE_VALUES.length];
    }
}