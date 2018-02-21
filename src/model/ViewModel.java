package model;

import control.KeyButton;
import control.KeyControl;
import control.MousePosControl;
import model.segment.Segment;
import model.segment.SegmentEditable;
import modelviewer.Selector;
import shape.CubeInstancedFaces;
import util.LList;
import util.math.MathAngles;

public class ViewModel {
    private static final float TRANSLATION_SPEED = .2f, ROTATION_SPEED = MathAngles.PI / 12, SCALE_SPEED = TRANSLATION_SPEED * 2;
    private static final float[] UNSELECTED_COLOR = new float[] {1, 1, 1, 1}, SELECTED_COLOR = new float[] {0, 0, 1, 1};

    private LList<SegmentEditable> segments;
    private LList<SegmentEditable>.Node selectedSegmentNode;

    public ViewModel() {
        segments = new LList<>();
    }

    public ViewModel(ModelData modelData, CubeInstancedFaces cubeInstancedFaces) {
        this();
        SegmentEditable[] segments = new SegmentEditable[modelData.segmentCount];
        for (int i = 0; i < modelData.segmentCount; i++) {
            segments[i] = new SegmentEditable(modelData.segmentData[i]);
            addSegment(segments[i]);
        }

        for (int i = 0; i < modelData.segmentCount; i++)
            segments[i].init(modelData.parents[i] != -1 ? segments[modelData.parents[i]] : null, cubeInstancedFaces);
    }

    public void addSegment(SegmentEditable segment) {
        if (selectedSegmentNode == null) {
            selectedSegmentNode = segments.addTail(segment);
            segment.setColor(SELECTED_COLOR);

        } else {
            segments.addTail(segment);
            segment.setColor(UNSELECTED_COLOR);
        }
    }

    public float[] normalizeControl(KeyControl keyControl) {
        float x = 0, y = 0, z = 0, theta = 0;

        if (keyControl.isKeyPressed(KeyButton.KEY_W))
            y += TRANSLATION_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_S))
            y -= TRANSLATION_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_A))
            x -= TRANSLATION_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_D))
            x += TRANSLATION_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_SHIFT))
            z -= TRANSLATION_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_SPACE))
            z += TRANSLATION_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_Q))
            theta += ROTATION_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_E))
            theta -= ROTATION_SPEED;

        return new float[] {x, y, z, theta};
    }

    public float[] normalizeControl(MousePosControl mousePosControl, boolean shift) {
        float x = 0, y = 0, z = 0;
        if (shift)
            z -= TRANSLATION_SPEED * mousePosControl.getMoveY();
        else {
            y -= TRANSLATION_SPEED * mousePosControl.getMoveY();
            x += TRANSLATION_SPEED * mousePosControl.getMoveX();
        }

        return new float[] {x, y, z, 0};
    }

    public void update(Selector.Tool tool, float[] normalizedControl) {
        switch (tool) {
            case POSITION:
                updatePosition(normalizedControl);
                break;
            case SCALE:
                updateScale(normalizedControl);
                break;
        }
    }

    public void updtaeSelectedSegment(int selectedSegmentDelta) {
        updateSelectedSegment(selectedSegmentDelta);
    }

    private void updateSelectedSegment(int selectedSegmentDelta) {
        if (selectedSegmentDelta > 0 && selectedSegmentNode.getNext() != null) {
            selectedSegmentNode.getValue().setColor(UNSELECTED_COLOR);
            selectedSegmentNode = selectedSegmentNode.getNext();
            selectedSegmentNode.getValue().setColor(SELECTED_COLOR);

        } else if (selectedSegmentDelta < 0 && selectedSegmentNode.getPrev() != null) {
            selectedSegmentNode.getValue().setColor(UNSELECTED_COLOR);
            selectedSegmentNode = selectedSegmentNode.getPrev();
            selectedSegmentNode.getValue().setColor(SELECTED_COLOR);
        }
    }

    private void updatePosition(float[] normalizedControl) {
        selectedSegmentNode.getValue().addTranslation(normalizedControl[0] * TRANSLATION_SPEED, normalizedControl[1] * TRANSLATION_SPEED, normalizedControl[2] * TRANSLATION_SPEED);
        selectedSegmentNode.getValue().addRotation(normalizedControl[3] * ROTATION_SPEED);
    }

    private void updateScale(float[] normalizedControl) {
        selectedSegmentNode.getValue().addScale((normalizedControl[0] + normalizedControl[3]) * SCALE_SPEED, (normalizedControl[1] + normalizedControl[3]) * SCALE_SPEED, (normalizedControl[2] + normalizedControl[3]) * SCALE_SPEED);
    }

    public void draw() {
        for (Segment segment : segments)
            segment.draw();
    }

    public ModelData getModelData() {
        ModelData modelData = new ModelData(segments.size());

        int segmentIndex = 0;
        for (SegmentEditable segment : segments) {
            modelData.parents[segmentIndex] = -1;
            int match = 0;
            for (SegmentEditable segmentInner : segments) {
                if (segment.getParent() == segmentInner) {
                    modelData.parents[segmentIndex] = match;
                    break;
                }
                match++;
            }

            modelData.segmentData[segmentIndex] = segment.getSegmentData();

            segmentIndex++;
        }

        return modelData;
    }
}