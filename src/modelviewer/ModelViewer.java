package modelviewer;

import camera.Camera;
import camera.FreeCameraFollow;
import control.*;
import engine.Engine;
import engine.EngineRunnable;
import model.ModelData;
import model.ViewModel;
import model.segment.SegmentEditable;
import shader.ShaderManager;
import shape.CubeInstancedFaces;
import ui.UiDrawerModelViewer;
import util.Writer;

import java.io.IOException;

public class ModelViewer implements EngineRunnable {
    private static final String MODEL_FILE = "viewModel.model";

    private KeyControl keyControl;
    private MousePosControl mousePosControl;
    private MouseButtonControl mouseButtonControl;

    private CubeInstancedFaces cubeInstancedFaces;
    private FreeCameraFollow follow;
    private Camera camera;
    private ViewModel viewModel;
    private Selector selector;
    private UiDrawerModelViewer uiDrawer;

    @Override
    public void init(KeyControl keyControl, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        this.keyControl = keyControl;
        this.mousePosControl = mousePosControl;
        this.mouseButtonControl = mouseButtonControl;
        mousePosControl.unlock();

        ShaderManager.setRenderShader();

        camera = new Camera(ShaderManager.getRenderShaderProgramId(), 20, 0);
        follow = new FreeCameraFollow();
        camera.setFollow(follow);

        createViewModel();

        selector = new Selector();
        uiDrawer = new UiDrawerModelViewer(selector, keyControl, mousePosControl, mouseButtonControl);
    }

    @Override
    public void printHelp() {
        System.out.println("W A S D move / scale horizontally");
        System.out.println("Q E rotate");
        System.out.println("SPACE SHIFT move / scale vertically");
        System.out.println("ENTER selector menu");
        System.out.println("N prev segment");
        System.out.println("M next segment");
        System.out.println("B new");
        System.out.println("C load");
        System.out.println("V store");
        System.out.println("DRAG MOUSE SECONDARY rotate camera");
        System.out.println("Z X zoom camera");
        System.out.println("R F move camera vertically");
    }

    private void storeViewModel() {
        try {
            Writer.getWriteStream(MODEL_FILE).writeObject(viewModel.getModelData());
            System.out.println("stored view model " + MODEL_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadViewModel() {
        cubeInstancedFaces = new CubeInstancedFaces();
        viewModel = new ViewModel(ModelData.readModelData(MODEL_FILE), cubeInstancedFaces);
        System.out.println("loaded view model " + MODEL_FILE);
    }

    private void createViewModel() {
        viewModel = new ViewModel();
        cubeInstancedFaces = new CubeInstancedFaces();

        SegmentEditable body = new SegmentEditable();
        SegmentEditable head = new SegmentEditable();
        SegmentEditable tail = new SegmentEditable();
        SegmentEditable legFR = new SegmentEditable();
        SegmentEditable legFL = new SegmentEditable();
        SegmentEditable legBR = new SegmentEditable();
        SegmentEditable legBL = new SegmentEditable();

        body.init(null, cubeInstancedFaces);
        head.init(body, cubeInstancedFaces);
        tail.init(body, cubeInstancedFaces);
        legFR.init(body, cubeInstancedFaces);
        legFL.init(body, cubeInstancedFaces);
        legBR.init(body, cubeInstancedFaces);
        legBL.init(body, cubeInstancedFaces);

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

        viewModel.addSegment(body);
        viewModel.addSegment(head);
        viewModel.addSegment(tail);
        viewModel.addSegment(legFR);
        viewModel.addSegment(legFL);
        viewModel.addSegment(legBR);
        viewModel.addSegment(legBL);
    }

    @Override
    public void loop() {
        if (keyControl.isKeyPressed(KeyButton.KEY_B))
            createViewModel();
        if (keyControl.isKeyPressed(KeyButton.KEY_C))
            loadViewModel();
        if (keyControl.isKeyPressed(KeyButton.KEY_V))
            storeViewModel();

        ShaderManager.setRenderShader();

        if (mouseButtonControl.isMousePressed(MouseButton.SECONDARY))
            mousePosControl.lock();
        else if (mouseButtonControl.isMouseReleased(MouseButton.SECONDARY))
            mousePosControl.unlock();

        follow.update(mousePosControl);
        camera.update(keyControl);
        selector.update(keyControl);
        viewModel.update(selector.getSelectedSegmentDelta(), selector.getSelectedTool(), keyControl);

        cubeInstancedFaces.reset();
        viewModel.draw();
        cubeInstancedFaces.doneAdding();
        cubeInstancedFaces.draw();

        uiDrawer.update();
        ShaderManager.setUiShader();
        uiDrawer.draw();
        ShaderManager.setTextShader();
        uiDrawer.drawText();

        mouseButtonControl.next();
    }

    @Override
    public void updateFps(int fps) {
        uiDrawer.updateFps(fps);
    }

    @Override
    public void shutDown() {
    }

    public static void main(String[] args) {
        new Engine(new ModelViewer());
    }
}