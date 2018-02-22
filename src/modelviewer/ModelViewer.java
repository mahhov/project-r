package modelviewer;

import camera.Camera;
import camera.FreeCameraFollow;
import control.Controls;
import control.KeyButton;
import control.MouseButton;
import engine.Engine;
import engine.EngineRunnable;
import model.ModelData;
import model.ViewModel;
import shader.ShaderManager;
import shape.CubeInstancedFaces;
import ui.UiDrawerModelViewer;
import util.Writer;

import java.io.IOException;

public class ModelViewer implements EngineRunnable {
    private static final String MODEL_FILE = "temp.model";

    private Controls controls;

    private CubeInstancedFaces cubeInstancedFaces;
    private FreeCameraFollow follow;
    private Camera camera;
    private ViewModel viewModel;
    private Selector selector;
    private UiDrawerModelViewer uiDrawer;

    @Override
    public void init(Controls controls) {
        this.controls = controls;
        controls.mousePosControl.unlock();

        ShaderManager.setRenderShader();

        camera = new Camera(ShaderManager.getRenderShaderProgramId(), 20, 0);
        follow = new FreeCameraFollow();
        camera.setFollow(follow);

        createViewModel();

        selector = new Selector();
        uiDrawer = new UiDrawerModelViewer(selector, controls.keyControl, controls.mousePosControl, controls.mouseButtonControl);
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
        System.out.println("DRAG MOUSE PRIMARY translate / rotate / scale segment");
        System.out.println("Z X zoom camera");
        System.out.println("R F move camera vertically");
        System.out.println("SCROLL WHEEL change selected segment");
        System.out.println("SCROLL WHEEL HORIZONTAL change selected tool");
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
        cubeInstancedFaces = new CubeInstancedFaces();
        viewModel = ViewModelProvider.fourLeg(cubeInstancedFaces);
    }

    @Override
    public void loop() {
        if (controls.keyControl.isKeyPressed(KeyButton.KEY_B))
            createViewModel();
        if (controls.keyControl.isKeyPressed(KeyButton.KEY_C))
            loadViewModel();
        if (controls.keyControl.isKeyPressed(KeyButton.KEY_V))
            storeViewModel();

        ShaderManager.setRenderShader();

        uiDrawer.update();

        if (controls.mouseButtonControl.isMousePressed(MouseButton.PRIMARY) || controls.mouseButtonControl.isMousePressed(MouseButton.SECONDARY))
            controls.mousePosControl.lock();
        else if (controls.mouseButtonControl.isMouseReleased(MouseButton.PRIMARY) || controls.mouseButtonControl.isMouseReleased(MouseButton.SECONDARY))
            controls.mousePosControl.unlock();
        if (controls.mouseButtonControl.isMouseDown(MouseButton.PRIMARY))
            viewModel.update(selector.getSelectedTool(), viewModel.normalizeControl(controls.mousePosControl, controls.keyControl.isKeyDown(KeyButton.KEY_SHIFT)));
        if (controls.mouseButtonControl.isMouseDown(MouseButton.SECONDARY))
            follow.update(controls.mousePosControl);

        camera.update(controls.keyControl);
        selector.update(controls.keyControl, controls.mouseScrollControl);
        viewModel.update(selector.getSelectedTool(), viewModel.normalizeControl(controls.keyControl));
        viewModel.updtaeSelectedSegment(selector.getSelectedSegmentDelta());

        cubeInstancedFaces.reset();
        viewModel.draw();
        cubeInstancedFaces.doneAdding();
        cubeInstancedFaces.draw();

        ShaderManager.setUiShader();
        uiDrawer.draw();
        ShaderManager.setTextShader();
        uiDrawer.drawText();

        controls.mouseButtonControl.next();
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