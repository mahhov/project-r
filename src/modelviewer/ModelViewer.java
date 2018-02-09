package modelviewer;

import camera.Camera;
import camera.Follow;
import character.model.Segment;
import character.model.ViewModel;
import control.KeyControl;
import control.MouseButtonControl;
import control.MousePosControl;
import shader.ShaderManager;
import shape.CubeInstancedFaces;
import util.MathAngles;

public class ModelViewer {
    private KeyControl keyControl;
    private MousePosControl mousePosControl;
    private MouseButtonControl mouseButtonControl;

    private CubeInstancedFaces cubeInstancedFaces;
    private Camera camera;
    private ViewModel viewModel;

    ModelViewer(KeyControl keyControl, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        this.keyControl = keyControl;
        this.mousePosControl = mousePosControl;
        this.mouseButtonControl = mouseButtonControl;

        ShaderManager.setRenderShader();

        camera = new Camera(ShaderManager.getRenderShaderProgramId());
        camera.setFollow(new Follow() {
            @Override
            public float getFollowX() {
                return 0;
            }

            @Override
            public float getFollowY() {
                return 0;
            }

            @Override
            public float getFollowZ() {
                return 0;
            }

            @Override
            public float getFollowTheta() {
                return 0;
            }

            @Override
            public float getFollowThetaZ() {
                return 0;
            }

            @Override
            public float[] getFollowNorm() {
                float theta = getFollowTheta();
                float thetaZ = getFollowThetaZ();

                float[] norm = new float[2];
                norm[0] = -MathAngles.sin(theta);
                norm[1] = MathAngles.cos(theta);

                float thetaZCos = MathAngles.cos(thetaZ);
                return new float[] {norm[0] * thetaZCos, MathAngles.sin(thetaZ), -norm[1] * thetaZCos};
            }

            @Override
            public boolean isFollowZoom() {
                return false;
            }
        });

        viewModel = new ViewModel();
        cubeInstancedFaces = new CubeInstancedFaces();
        float[] color = new float[] {1, 1, 1, 1};
        viewModel.addSegment(new Segment(null, 1, color, cubeInstancedFaces));
    }

    void loop() {
        ShaderManager.setRenderShader();
        camera.update(keyControl);
        
        cubeInstancedFaces.reset();
        viewModel.draw();
        cubeInstancedFaces.doneAdding();
        cubeInstancedFaces.draw();
        mouseButtonControl.next();
    }
}