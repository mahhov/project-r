package model;

import control.KeyButton;
import control.KeyControl;
import model.segment.Segment;
import model.segment.SegmentData;
import model.segment.SegmentEditable;
import modelviewer.Selector;
import shape.CubeInstancedFaces;
import util.LList;
import util.math.MathAngles;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ViewModel {
    private static final float TRANSLATION_SPEED = .2f, ROTATION_SPEED = MathAngles.PI / 12, SCALE_SPEED = TRANSLATION_SPEED * 2;
    private static final float[] UNSELECTED_COLOR = new float[] {1, 1, 1, 1}, SELECTED_COLOR = new float[] {0, 0, 1, 1};

    private LList<SegmentEditable> segments;
    private LList<SegmentEditable>.Node selectedSegmentNode;

    public ViewModel() {
        segments = new LList<>();
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

    public void update(int selectedSegmentDelta, Selector.Tool tool, KeyControl keyControl) {
        updateSelectedSegment(selectedSegmentDelta);

        switch (tool) {
            case POSITION:
                updatePosition(keyControl);
                break;
            case SCALE:
                updateScale(keyControl);
                break;
        }
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

    private void updatePosition(KeyControl keyControl) {
        float dx = 0, dy = 0, dz = 0, dtheta = 0;

        if (keyControl.isKeyPressed(KeyButton.KEY_W))
            dy += TRANSLATION_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_S))
            dy -= TRANSLATION_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_A))
            dx -= TRANSLATION_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_D))
            dx += TRANSLATION_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_SHIFT))
            dz -= TRANSLATION_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_SPACE))
            dz += TRANSLATION_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_Q))
            dtheta += ROTATION_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_E))
            dtheta -= ROTATION_SPEED;

        selectedSegmentNode.getValue().addTranslation(dx, dy, dz);
        selectedSegmentNode.getValue().addRotation(dtheta);
    }

    private void updateScale(KeyControl keyControl) {
        float dx = 0, dy = 0, dz = 0;

        if (keyControl.isKeyPressed(KeyButton.KEY_W))
            dy += SCALE_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_S))
            dy -= SCALE_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_A))
            dx -= SCALE_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_D))
            dx += SCALE_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_SHIFT))
            dz -= SCALE_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_SPACE))
            dz += SCALE_SPEED;

        if (keyControl.isKeyPressed(KeyButton.KEY_Q)) {
            dx += SCALE_SPEED;
            dy += SCALE_SPEED;
            dz += SCALE_SPEED;
        }

        if (keyControl.isKeyPressed(KeyButton.KEY_E)) {
            dx -= SCALE_SPEED;
            dy -= SCALE_SPEED;
            dz -= SCALE_SPEED;
        }

        selectedSegmentNode.getValue().addScale(dx, dy, dz);
    }

    public void draw() {
        for (Segment segment : segments)
            segment.draw();
    }

    public void write(ObjectOutputStream out) { // todo move these to Model & ViewModel common ancestor
        try {
            int segmentsCount = segments.size();
            out.writeInt(segmentsCount);

            int[] parents = new int[segmentsCount];
            int segmentIndex = 0;
            for (SegmentEditable segment : segments) {
                parents[segmentIndex] = -1;
                int match = 0;
                for (SegmentEditable segmentInner : segments) {
                    if (segment.getParent() == segmentInner) {
                        parents[segmentIndex] = match;
                        break;
                    }
                    match++;
                }
                segmentIndex++;
            }

            for (int i = 0; i < segmentsCount; i++)
                out.writeInt(parents[i]);

            for (SegmentEditable segment : segments)
                out.writeObject(segment.getSegmentData());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(ObjectInputStream in, CubeInstancedFaces cubeInstancedFaces) {
        try {
            int segmentsCount = in.readInt();

            int[] parents = new int[segmentsCount];
            for (int i = 0; i < segmentsCount; i++)
                parents[i] = in.readInt();

            SegmentEditable segments[] = new SegmentEditable[segmentsCount];
            for (int i = 0; i < segmentsCount; i++) {
                segments[i] = new SegmentEditable((SegmentData) in.readObject());
                addSegment(segments[i]);
            }

            for (int i = 0; i < segmentsCount; i++)
                segments[i].init(parents[i] != -1 ? segments[parents[i]] : null, cubeInstancedFaces);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}