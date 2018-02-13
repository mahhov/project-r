package modelviewer;

import camera.Camera;
import camera.FreeCameraFollow;
import character.model.ViewModel;
import character.model.segment.SegmentEditable;
import control.KeyControl;
import control.MouseButtonControl;
import control.MousePosControl;
import engine.Engine;
import engine.EngineRunnable;
import shader.ShaderManager;
import shape.CubeInstancedFaces;
import ui.UiDrawerModelViewer;

public class ModelViewer implements EngineRunnable {
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

        ShaderManager.setRenderShader();

        camera = new Camera(ShaderManager.getRenderShaderProgramId(), 20, 0);
        follow = new FreeCameraFollow();
        camera.setFollow(follow);
        viewModel = createViewModel();
        selector = new Selector();
        uiDrawer = new UiDrawerModelViewer(selector, keyControl, mousePosControl, mouseButtonControl);
    }

    private ViewModel createViewModel() {
        ViewModel viewModel = new ViewModel();
        cubeInstancedFaces = new CubeInstancedFaces();
        float[] color = new float[] {1, 1, 1, 1};

        SegmentEditable body = new SegmentEditable(null, color, cubeInstancedFaces);
        SegmentEditable head = new SegmentEditable(body, color, cubeInstancedFaces);
        SegmentEditable legFR = new SegmentEditable(body, color, cubeInstancedFaces);
        SegmentEditable legFL = new SegmentEditable(body, color, cubeInstancedFaces);
        SegmentEditable legBR = new SegmentEditable(body, color, cubeInstancedFaces);
        SegmentEditable legBL = new SegmentEditable(body, color, cubeInstancedFaces);

        body.setScale(2, 2.5f, 2);
        head.setScale(1.3f, 1.3f, 1.3f);
        legFR.setScale(.8f, .8f, .8f);
        legFL.setScale(.8f, .8f, .8f);
        legBR.setScale(.8f, .8f, .8f);
        legBL.setScale(.8f, .8f, .8f);

        body.setTranslation(0, 0, 0);
        head.setTranslation(0, 1.5f, 2);
        legFR.setTranslation(1, 1, -1.4f);
        legFL.setTranslation(-1, 1, -1.4f);
        legBR.setTranslation(1, -1, -1.4f);
        legBL.setTranslation(-1, -1, -1.4f);

        viewModel.addSegment(body);
        viewModel.addSegment(head);
        viewModel.addSegment(legFR);
        viewModel.addSegment(legFL);
        viewModel.addSegment(legBR);
        viewModel.addSegment(legBL);

        return viewModel;
    }

    @Override
    public void loop() {
        ShaderManager.setRenderShader();
        follow.update(mousePosControl);
        camera.update(keyControl);
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