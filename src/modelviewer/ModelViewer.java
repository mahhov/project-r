package modelviewer;

import camera.Camera;
import camera.FreeCameraFollow;
import character.model.Segment;
import character.model.ViewModel;
import control.KeyControl;
import control.MouseButtonControl;
import control.MousePosControl;
import shader.ShaderManager;
import shape.CubeInstancedFaces;

public class ModelViewer {
    private KeyControl keyControl;
    private MousePosControl mousePosControl;
    private MouseButtonControl mouseButtonControl;

    private CubeInstancedFaces cubeInstancedFaces;
    public FreeCameraFollow follow;
    private Camera camera;
    private ViewModel viewModel;

    ModelViewer(KeyControl keyControl, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        this.keyControl = keyControl;
        this.mousePosControl = mousePosControl;
        this.mouseButtonControl = mouseButtonControl;

        ShaderManager.setRenderShader();

        camera = new Camera(ShaderManager.getRenderShaderProgramId());
        follow = new FreeCameraFollow();
        camera.setFollow(follow);

        viewModel = new ViewModel();
        cubeInstancedFaces = new CubeInstancedFaces();
        float[] color = new float[] {1, 1, 1, 1};
        viewModel.addSegment(new Segment(null, 1, color, cubeInstancedFaces));
    }

    void loop() {
        ShaderManager.setRenderShader();
        follow.update(keyControl);
        camera.update(keyControl);

        cubeInstancedFaces.reset();
        viewModel.draw();
        cubeInstancedFaces.doneAdding();
        cubeInstancedFaces.draw();
        mouseButtonControl.next();
    }
}