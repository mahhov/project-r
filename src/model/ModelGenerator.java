package model;

import model.animation.AnimationCreator;
import model.segment.SegmentEditable;
import util.Writer;
import util.math.MathArrays;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ModelGenerator {
    private enum ModelType {
        BUG("bug.model", bug()),
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

        body.setScale(3, 7.9f, 4);
        shell.setScale(5, 7, 4);
        head.setScale(3, 4, 3);
        horn.setScale(1, 1, 4);
        eyeL.setScale(1, 1, 1);
        eyeR.setScale(1, 1, 1);
        legFL.setScale(1, 1, 4);
        legFR.setScale(1, 1, 4);
        legML.setScale(1, 1, 4);
        legMR.setScale(1, 1, 4);
        legBL.setScale(1, 1, 4);
        legBR.setScale(1, 1, 4);

        body.setTranslation(0, 0, 0);
        shell.setTranslation(0, .5f, 1);
        head.setTranslation(0, 6, -.5f);
        horn.setTranslation(0, 8.5f, 1);
        eyeL.setTranslation(-2, 6.5f, -.5f);
        eyeR.setTranslation(2, 6.5f, -.5f);
        legFL.setTranslation(-3, 3.5f, -2);
        legFR.setTranslation(3, 3.5f, -2);
        legML.setTranslation(-3, .5f, -2);
        legMR.setTranslation(3, .5f, -2);
        legBL.setTranslation(-3, -2.5f, -2);
        legBR.setTranslation(3, -2.5f, -2);

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

        AnimationCreator animationCreator = new AnimationCreator(4, 12);
        animationCreator.setTotalTime(0, 10);
        animationCreator.setTotalTime(1, 10);
        animationCreator.setTotalTime(2, 10);
        animationCreator.setTotalTime(3, 10);
        animationCreator.setFrame(1, 6, 0, 1, 0, 0);
        animationCreator.setFrame(1, 8, 0, 1, 0, 0);
        animationCreator.setFrame(1, 10, 0, 1, 0, 0);
        animationCreator.setFrame(3, 7, 0, 1, 0, 0);
        animationCreator.setFrame(3, 9, 0, 1, 0, 0);
        animationCreator.setFrame(3, 11, 0, 1, 0, 0);
        modelCreator.setAnimationData(animationCreator);

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

        AnimationCreator animationCreator = new AnimationCreator(1, 7);
        //        animationCreator.setTotalTime(0, 20);
        //        animationCreator.setFrame(0, 1, 0, 0, 1, 0);
        modelCreator.setAnimationData(animationCreator);

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

        modelCreator.setAnimationData(new AnimationCreator(2, 6));

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

        modelCreator.setAnimationData(new AnimationCreator(1, 5));

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

        AnimationCreator animationCreator = new AnimationCreator(1, 1);
        modelCreator.setAnimationData(animationCreator);

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