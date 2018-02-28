package modelviewer;

import camera.Camera;
import camera.FreeCameraFollow;
import control.Controls;
import control.MouseButton;
import engine.Engine;
import engine.EngineRunnable;
import model.Model;
import model.ModelData;
import model.ModelGenerator;
import shader.ShaderManager;
import shape.CubeInstancedFaces;

public class ModelViewer implements EngineRunnable {
    private final ModelData.ModelType MODAL_TYPE = ModelData.ModelType.BUG;

    private Controls controls;
    private FreeCameraFollow follow;
    private Camera camera;
    private CubeInstancedFaces cubeInstancedFaces;
    private Model model;

    private boolean animate;

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
        System.out.println("--");
        String format = "%-25s%s\n";
        System.out.printf(format, "CLICK MOUSE PRIMARY", "play & pause animation");
        System.out.printf(format, "DRAG MOUSE SECONDARY", "rotate camera");
        System.out.printf(format, "Z X", "zoom camera");
        System.out.printf(format, "SCROLL", "WHEEL zoom model");
        System.out.printf(format, "R F", "move camera vertically");
    }

    @Override
    public void loop() {
        if (controls.mouseButtonControl.isMousePressed(MouseButton.SECONDARY))
            controls.mousePosControl.lock();
        else if (controls.mouseButtonControl.isMouseReleased(MouseButton.SECONDARY))
            controls.mousePosControl.unlock();
        if (controls.mouseButtonControl.isMouseDown(MouseButton.SECONDARY))
            follow.update(controls.mousePosControl);
        if (controls.mouseButtonControl.isMouseReleased(MouseButton.PRIMARY))
            animate = !animate;

        if (animate)
            model.animateWalk();
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
        ModelGenerator.generate();
        new Engine(new ModelViewer());
    }
}