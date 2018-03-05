package model;

import model.animation.AnimationCreator;
import model.animation.AnimationSet;
import model.animation.AnimationSetCreator;
import model.segment.SegmentEditable;
import util.Writer;
import util.math.MathArrays;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ModelGenerator {
    private enum ModelType {
        BUG("bug.model", bug()),
        MECH("mech.model", mech()),
        GOAT("goat.model", goat()),
        FOUR_LEG("fourLeg.model", fourLeg()),
        BIRD("bird.model", bird()),
        PILLAR("pillar.model", pillar());

        private final String file;
        private final ModelData modelData;

        ModelType(String file, ModelData modelData) {
            this.file = file;
            this.modelData = modelData;
        }
    }

    private static ModelData bug() {
        ModelCreator modelCreator = new ModelCreator();

        SegmentEditable body = new SegmentEditable(); // 0
        SegmentEditable shell = new SegmentEditable(); // 1
        SegmentEditable head = new SegmentEditable(); // 2
        SegmentEditable horn = new SegmentEditable(); // 3
        SegmentEditable eyeL = new SegmentEditable(); // 4
        SegmentEditable eyeR = new SegmentEditable(); // 5
        SegmentEditable legFL = new SegmentEditable(); // 6
        SegmentEditable legFR = new SegmentEditable(); // 7
        SegmentEditable legML = new SegmentEditable(); // 8
        SegmentEditable legMR = new SegmentEditable(); // 9
        SegmentEditable legBL = new SegmentEditable(); // 10
        SegmentEditable legBR = new SegmentEditable(); // 11

        float lightScale = 2;
        float[] darkColor = new float[] {.4f, .2f, .1f, 1};
        float[] lightColor = new float[] {.5f, .3f, .2f, 1};
        MathArrays.scale(darkColor, lightScale);
        MathArrays.scale(lightColor, lightScale);
        body.setColor(darkColor);
        shell.setColor(lightColor);
        head.setColor(darkColor);
        horn.setColor(darkColor);
        eyeL.setColor(lightColor);
        eyeR.setColor(lightColor);
        legFL.setColor(lightColor);
        legFR.setColor(lightColor);
        legML.setColor(lightColor);
        legMR.setColor(lightColor);
        legBL.setColor(lightColor);
        legBR.setColor(lightColor);

        body.init(null, null);
        shell.init(body, null);
        head.init(body, null);
        horn.init(body, null);
        eyeL.init(body, null);
        eyeR.init(body, null);
        legFL.init(body, null);
        legFR.init(body, null);
        legML.init(body, null);
        legMR.init(body, null);
        legBL.init(body, null);
        legBR.init(body, null);

        body.setScale(3, 8, 4);
        shell.setScale(5, 8.1f, 4);
        head.setScale(3, 4, 3);
        horn.setScale(1, 1, 4);
        eyeL.setScale(1, 1, 1);
        eyeR.setScale(eyeL);
        legFL.setScale(1, 1, 4);
        legFR.setScale(legFL);
        legML.setScale(legFL);
        legMR.setScale(legFL);
        legBL.setScale(legFL);
        legBR.setScale(legFL);

        shell.setTranslation(0, .5f, 1);
        head.frontOf(body).bottomAlign(body.bottom());
        horn.frontOf(head).topAlign(shell.top());
        eyeL.leftOf(head).translate(0, .5f, 0);
        eyeR.rightOf(head).translate(0, .5f, 0);
        legFL.leftOf(shell).translate(0, 3, -2);
        legFR.rightOf(shell).translate(0, 3, -2);
        legML.leftOf(shell).translate(0, 0, -2);
        legMR.rightOf(shell).translate(0, 0, -2);
        legBL.leftOf(shell).translate(0, -3, -2);
        legBR.rightOf(shell).translate(0, -3, -2);

        modelCreator.addSegment(body);
        modelCreator.addSegment(shell);
        modelCreator.addSegment(head);
        modelCreator.addSegment(horn);
        modelCreator.addSegment(eyeL);
        modelCreator.addSegment(eyeR);
        modelCreator.addSegment(legFL);
        modelCreator.addSegment(legFR);
        modelCreator.addSegment(legML);
        modelCreator.addSegment(legMR);
        modelCreator.addSegment(legBL);
        modelCreator.addSegment(legBR);

        AnimationCreator walkAnimationCreator = new AnimationCreator(4, 12);
        walkAnimationCreator.setTotalTime(0, 10);
        walkAnimationCreator.setTotalTime(1, 10);
        walkAnimationCreator.setTotalTime(2, 10);
        walkAnimationCreator.setTotalTime(3, 10);
        walkAnimationCreator.setFrame(1, 6, 0, 1, 0, 0);
        walkAnimationCreator.setFrame(1, 8, 0, 1, 0, 0);
        walkAnimationCreator.setFrame(1, 10, 0, 1, 0, 0);
        walkAnimationCreator.setFrame(3, 7, 0, 1, 0, 0);
        walkAnimationCreator.setFrame(3, 9, 0, 1, 0, 0);
        walkAnimationCreator.setFrame(3, 11, 0, 1, 0, 0);

        AnimationSetCreator animationSetCreator = new AnimationSetCreator(12);
        animationSetCreator.setAnimationData(AnimationSet.AnimationType.WALK, walkAnimationCreator);
        modelCreator.setAnimationSetData(animationSetCreator);

        return modelCreator.getModelData();
    }

    private static ModelData mech() {
        ModelCreator modelCreator = new ModelCreator();

        SegmentEditable body = new SegmentEditable(); // 0
        SegmentEditable head = new SegmentEditable(); // 1
        SegmentEditable tail = new SegmentEditable(); // 2
        SegmentEditable legL = new SegmentEditable(); // 3
        SegmentEditable legR = new SegmentEditable(); // 4
        SegmentEditable footL = new SegmentEditable(); // 5
        SegmentEditable footR = new SegmentEditable(); // 6
        SegmentEditable armL = new SegmentEditable(); // 7
        SegmentEditable armR = new SegmentEditable(); // 8
        SegmentEditable handL = new SegmentEditable(); // 9
        SegmentEditable handR = new SegmentEditable(); // 10

        float lightScale = 2;
        float[] darkColor = new float[] {.3f, .3f, .3f, 1};
        float[] lightColor = new float[] {.6f, .6f, .6f, 1};
        MathArrays.scale(darkColor, lightScale);
        MathArrays.scale(lightColor, lightScale);
        body.setColor(darkColor);
        head.setColor(darkColor);
        tail.setColor(darkColor);
        legL.setColor(lightColor);
        legR.setColor(lightColor);
        footL.setColor(lightColor);
        footR.setColor(lightColor);
        armL.setColor(lightColor);
        armR.setColor(lightColor);
        handL.setColor(lightColor);
        handR.setColor(lightColor);

        body.init(null, null);
        head.init(body, null);
        tail.init(body, null);
        legL.init(body, null);
        legR.init(body, null);
        footL.init(body, null);
        footR.init(body, null);
        armL.init(body, null);
        armR.init(body, null);
        handL.init(body, null);
        handR.init(body, null);

        body.setScale(2, 2, 3);
        head.setScale(2, 3, 2);
        tail.setScale(2, 1, 2);
        legL.setScale(1, 1, 3);
        legR.setScale(legL);
        footL.setScale(1, 4, 1);
        footR.setScale(footL);
        armL.setScale(1, 2, 1);
        armR.setScale(armL);
        handL.setScale(1, 4, 1);
        handR.setScale(handL);

        head.topOf(body).backAlign(body.back());
        tail.backOf(head).translate(0, 0, -1);
        legL.bottomOf(body).rightAlign(body.left()).backAlign(body.back()).translate(0, 0, 1);
        legR.bottomOf(body).leftAlign(body.right()).backAlign(body.back()).translate(0, 0, 1);
        footL.bottomOf(legL).backAlign(tail.back());
        footR.bottomOf(legR).backAlign(tail.back());
        armL.topOf(body).rightAlign(body.left());
        armR.topOf(body).leftAlign(body.right());
        handL.bottomOf(armL).backAlign(armL.back());
        handR.bottomOf(armR).backAlign(armR.back());

        modelCreator.addSegment(body);
        modelCreator.addSegment(head);
        modelCreator.addSegment(tail);
        modelCreator.addSegment(legL);
        modelCreator.addSegment(legR);
        modelCreator.addSegment(footL);
        modelCreator.addSegment(footR);
        modelCreator.addSegment(armL);
        modelCreator.addSegment(armR);
        modelCreator.addSegment(handL);
        modelCreator.addSegment(handR);

        AnimationCreator walkAnimationCreator = new AnimationCreator(2, 11);
        walkAnimationCreator.setTotalTime(0, 20);
        walkAnimationCreator.setTotalTime(1, 20);

        float legFront = .5f, legBack = 0;
        float armFront = .25f, armBack = 0f;

        walkAnimationCreator.setFrame(0, 3, 0, legFront, 0, 0);
        walkAnimationCreator.setFrame(0, 5, 0, legFront, 0, 0);
        walkAnimationCreator.setFrame(0, 8, 0, armFront, 0, 0);
        walkAnimationCreator.setFrame(0, 10, 0, armFront, 0, 0);

        walkAnimationCreator.setFrame(0, 4, 0, legBack, 0, 0);
        walkAnimationCreator.setFrame(0, 6, 0, legBack, 0, 0);
        walkAnimationCreator.setFrame(0, 7, 0, armBack, 0, 0);
        walkAnimationCreator.setFrame(0, 9, 0, armBack, 0, 0);

        walkAnimationCreator.setFrame(1, 3, 0, legBack, 0, 0);
        walkAnimationCreator.setFrame(1, 5, 0, legBack, 0, 0);
        walkAnimationCreator.setFrame(1, 8, 0, armBack, 0, 0);
        walkAnimationCreator.setFrame(1, 10, 0, armBack, 0, 0);

        walkAnimationCreator.setFrame(1, 4, 0, legFront, 0, 0);
        walkAnimationCreator.setFrame(1, 6, 0, legFront, 0, 0);
        walkAnimationCreator.setFrame(1, 7, 0, armFront, 0, 0);
        walkAnimationCreator.setFrame(1, 9, 0, armFront, 0, 0);

        AnimationSetCreator animationSetCreator = new AnimationSetCreator(11);
        animationSetCreator.setAnimationData(AnimationSet.AnimationType.WALK, walkAnimationCreator);
        modelCreator.setAnimationSetData(animationSetCreator);

        return modelCreator.getModelData();
    }

    private static ModelData goat() {
        ModelCreator modelCreator = new ModelCreator();

        SegmentEditable body = new SegmentEditable();
        SegmentEditable head = new SegmentEditable();
        SegmentEditable tail = new SegmentEditable();
        SegmentEditable legFR = new SegmentEditable();
        SegmentEditable legFL = new SegmentEditable();
        SegmentEditable legBR = new SegmentEditable();
        SegmentEditable legBL = new SegmentEditable();

        float[] color = new float[] {0, 1, 0, 1};
        body.setColor(color);
        head.setColor(color);
        tail.setColor(color);
        legFR.setColor(color);
        legFL.setColor(color);
        legBR.setColor(color);
        legBL.setColor(color);

        body.init(null, null);
        head.init(body, null);
        tail.init(body, null);
        legFR.init(body, null);
        legFL.init(body, null);
        legBR.init(body, null);
        legBL.init(body, null);

        body.setScale(2, 2.5f, 2);
        head.setScale(1.3f, 1.3f, 1.3f);
        tail.setScale(1.3f, 2f, .3f);
        legFR.setScale(.8f, .8f, .8f);
        legFL.setScale(.8f, .8f, .8f);
        legBR.setScale(.8f, .8f, .8f);
        legBL.setScale(.8f, .8f, .8f);

        body.setTranslation(0, 0, 0);
        head.setTranslation(0, 1.5f, 2);
        tail.setTranslation(0, -1.5f, .3f);
        legFR.setTranslation(1, 1, -1.4f);
        legFL.setTranslation(-1, 1, -1.4f);
        legBR.setTranslation(1, -1, -1.4f);
        legBL.setTranslation(-1, -1, -1.4f);

        modelCreator.addSegment(body);
        modelCreator.addSegment(head);
        modelCreator.addSegment(tail);
        modelCreator.addSegment(legFR);
        modelCreator.addSegment(legFL);
        modelCreator.addSegment(legBR);
        modelCreator.addSegment(legBL);

        AnimationSetCreator animationSetCreator = new AnimationSetCreator(7);
        modelCreator.setAnimationSetData(animationSetCreator);

        return modelCreator.getModelData();
    }

    private static ModelData fourLeg() {
        ModelCreator modelCreator = new ModelCreator();

        SegmentEditable body = new SegmentEditable();
        SegmentEditable head = new SegmentEditable();
        SegmentEditable legFR = new SegmentEditable();
        SegmentEditable legFL = new SegmentEditable();
        SegmentEditable legBR = new SegmentEditable();
        SegmentEditable legBL = new SegmentEditable();

        float[] color = new float[] {1, 0, 0, 1};
        body.setColor(color);
        head.setColor(color);
        legFR.setColor(color);
        legFL.setColor(color);
        legBR.setColor(color);
        legBL.setColor(color);

        body.init(null, null);
        head.init(body, null);
        legFR.init(body, null);
        legFL.init(body, null);
        legBR.init(body, null);
        legBL.init(body, null);

        body.setScale(2, 6, 1);
        head.setScale(3, 1, 2);
        legFR.setScale(1, 1, 5);
        legFL.setScale(1, 1, 5);
        legBR.setScale(1, 1, 5);
        legBL.setScale(1, 1, 5);

        body.setTranslation(0, 0, 0);
        head.setTranslation(0, 3.5f, .5f);
        legFR.setTranslation(1.5f, 1.5f, -1);
        legFL.setTranslation(-1.5f, 1.5f, -1);
        legBR.setTranslation(1.5f, -1.5f, -1);
        legBL.setTranslation(-1.5f, -1.5f, -1);

        modelCreator.addSegment(body);
        modelCreator.addSegment(head);
        modelCreator.addSegment(legFR);
        modelCreator.addSegment(legFL);
        modelCreator.addSegment(legBR);
        modelCreator.addSegment(legBL);

        AnimationSetCreator animationSetCreator = new AnimationSetCreator(6);
        modelCreator.setAnimationSetData(animationSetCreator);

        return modelCreator.getModelData();
    }

    private static ModelData bird() {
        ModelCreator modelCreator = new ModelCreator();

        SegmentEditable body = new SegmentEditable();
        SegmentEditable legFR = new SegmentEditable();
        SegmentEditable legFL = new SegmentEditable();
        SegmentEditable legBR = new SegmentEditable();
        SegmentEditable legBL = new SegmentEditable();

        float[] color = new float[] {0, 1, 0, 1};
        body.setColor(color);
        legFR.setColor(color);
        legFL.setColor(color);
        legBR.setColor(color);
        legBL.setColor(color);

        body.init(null, null);
        legFR.init(body, null);
        legFL.init(body, null);
        legBR.init(body, null);
        legBL.init(body, null);

        body.setScale(1, 3, 1);
        legFR.setScale(2, 2, 1);
        legFL.setScale(2, 2, 1);
        legBR.setScale(2, 2, 1);
        legBL.setScale(2, 2, 1);

        body.setTranslation(0, 0, 0);
        legFR.setTranslation(1.5f, 1.5f, 0);
        legFL.setTranslation(-1.5f, 1.5f, 0);
        legBR.setTranslation(1.5f, -1.5f, 0);
        legBL.setTranslation(-1.5f, -1.5f, 0);

        modelCreator.addSegment(body);
        modelCreator.addSegment(legFR);
        modelCreator.addSegment(legFL);
        modelCreator.addSegment(legBR);
        modelCreator.addSegment(legBL);

        AnimationSetCreator animationSetCreator = new AnimationSetCreator(5);
        modelCreator.setAnimationSetData(animationSetCreator);

        return modelCreator.getModelData();
    }

    private static ModelData pillar() {
        System.out.println("creating pillar");

        ModelCreator modelCreator = new ModelCreator();

        SegmentEditable body = new SegmentEditable();

        float[] color = new float[] {0, 0, .3f, 1};
        body.setColor(color);

        body.init(null, null);

        body.setScale(1, 1, 10);

        body.setTranslation(0, 0, 0);

        modelCreator.addSegment(body);

        AnimationSetCreator animationSetCreator = new AnimationSetCreator(1);
        modelCreator.setAnimationSetData(animationSetCreator);

        return modelCreator.getModelData();
    }

    public static void generate() {
        for (ModelType modelType : ModelType.values()) {
            try (ObjectOutputStream objectOutputStream = Writer.getWriteStream(modelType.file)) {
                objectOutputStream.writeObject(modelType.modelData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}