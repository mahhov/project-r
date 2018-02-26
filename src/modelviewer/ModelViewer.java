package modelviewer;

import camera.Camera;
import camera.FreeCameraFollow;
import control.Controls;
import control.MouseButton;
import engine.Engine;
import engine.EngineRunnable;
import model.Model;
import model.ModelData;
import shader.ShaderManager;
import shape.CubeInstancedFaces;

public class ModelViewer implements EngineRunnable {
    private final ModelData.ModelType MODAL_TYPE = ModelData.ModelType.BIRD;

    private Controls controls;

    private FreeCameraFollow follow;
    private Camera camera;
    private CubeInstancedFaces cubeInstancedFaces;
    private Model model;

    @Override
    public void init(Controls controls) {
        this.controls = controls;
        controls.mousePosControl.unlock();

        ShaderManager.setRenderShader();

        follow = new FreeCameraFollow();
        camera = new Camera(ShaderManager.getRenderShaderProgramId(), 20, 0);
        camera.setFollow(follow);
        cubeInstancedFaces = new CubeInstancedFaces();
        model = new Model(MODAL_TYPE.modelData, cubeInstancedFaces);
    }

    @Override
    public void printHelp() {
        System.out.println("TODO : REPLACE THIS HELP");
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

    @Override
    public void loop() {
        if (controls.mouseButtonControl.isMousePressed(MouseButton.SECONDARY))
            controls.mousePosControl.lock();
        else if (controls.mouseButtonControl.isMouseReleased(MouseButton.SECONDARY))
            controls.mousePosControl.unlock();
        //        if (controls.mouseButtonControl.isMouseDown(MouseButton.PRIMARY))
        //            viewModel.update(selector.getSelectedTool(), viewModel.normalizeControl(controls.mousePosControl, controls.keyControl.isKeyDown(KeyButton.KEY_SHIFT)));
        if (controls.mouseButtonControl.isMouseDown(MouseButton.SECONDARY))
            follow.update(controls.mousePosControl);

        camera.update(controls.keyControl, controls.mouseScrollControl);

        cubeInstancedFaces.reset();
        model.draw();
        cubeInstancedFaces.doneAdding();
        cubeInstancedFaces.draw();

        controls.mouseButtonControl.next();
    }

    @Override
    public void updateFps(int fps) {
    }

    @Override
    public void shutDown() {
    }

    public static void main(String[] args) {
        new Engine(new ModelViewer());
    }
}