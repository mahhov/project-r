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

        camera = new Camera(ShaderManager.getRenderShaderProgramId(), 10, 0);
        follow = new FreeCameraFollow();
        camera.setFollow(follow);

        viewModel = new ViewModel();
        cubeInstancedFaces = new CubeInstancedFaces();
        float[] color = new float[] {1, 1, 1, 1};

        Segment body = new Segment(null, color, cubeInstancedFaces);
        Segment head = new Segment(body, color, cubeInstancedFaces);
        Segment legFR = new Segment(body, color, cubeInstancedFaces);
        Segment legFL = new Segment(body, color, cubeInstancedFaces);
        Segment legBR = new Segment(body, color, cubeInstancedFaces);
        Segment legBL = new Segment(body, color, cubeInstancedFaces);

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
    }

    void loop() {
        ShaderManager.setRenderShader();
        follow.update(keyControl, mousePosControl);
        camera.update(keyControl);

        cubeInstancedFaces.reset();
        viewModel.draw();
        cubeInstancedFaces.doneAdding();
        cubeInstancedFaces.draw();
        mouseButtonControl.next();
    }
}