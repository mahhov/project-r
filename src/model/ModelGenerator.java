package model;

import model.animation.AnimationCreator;
import model.segment.SegmentEditable;
import util.Writer;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ModelGenerator {
    private enum ModelType {
        GOAT("goat.model", goat()),
        FOUR_LEG("fourLeg.model", fourLeg()),
        BIRD("bird.model", bird());

        private final String file;
        private final ModelData modelData;

        ModelType(String file, ModelData modelData) {
            this.file = file;
            this.modelData = modelData;
        }
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

        AnimationCreator animationCreator = new AnimationCreator(2, 7);
        animationCreator.setTotalTime(0, 20);
        animationCreator.setTotalTime(1, 5);
        animationCreator.setFrame(0, 1, 0, 0, 1, 0);
        animationCreator.setFrame(1, 1, 0, 0, 0, 0);
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