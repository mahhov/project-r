package engine;

import camera.Camera;
import character.Human;
import control.KeyControl;
import control.MouseButtonControl;
import control.MousePosControl;
import map.Map;
import shader.ShaderManager;
import ui.UiDrawer;
import world.World;

public class Game {
    private KeyControl keyControl;
    private MousePosControl mousePosControl;
    private MouseButtonControl mouseButtonControl;

    private Map map;
    private World world;
    private Human human;
    private Camera camera;
    private UiDrawer uiDrawer;

    Game(KeyControl keyControl, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        this.keyControl = keyControl;
        this.mousePosControl = mousePosControl;
        this.mouseButtonControl = mouseButtonControl;

        ShaderManager.setRenderShader();
        camera = new Camera(ShaderManager.getRenderShaderProgramId());
        map = new Map(this);
        loadMap(0);
    }

    public void loadMap(int selected) {
        mousePosControl.lock();
        if (world != null)
            world.shutDownGeneratorExecutors();
        world = new World(64 * Engine.SCALE, 64 * Engine.SCALE, 16 * Engine.SCALE_Z, camera);
        human = new Human(32 * Engine.SCALE, 0, 8 * Engine.SCALE_Z, 0, 0, world.getIntersectionMover(), world.getIntersectionPicker(), keyControl, mousePosControl, mouseButtonControl);
        world.setHuman(human);
        world.addRandomMonsters(100);
        ShaderManager.setTextShader();
        uiDrawer = new UiDrawer(human, map, keyControl, mousePosControl, mouseButtonControl);
        camera.setFollow(human);
    }

    void loop() {
        ShaderManager.setRenderShader();
        camera.update(keyControl);
        world.setCameraCoordinate(camera.getWorldCoordinate());
        world.update();
        world.draw();

        uiDrawer.update();
        ShaderManager.setUiShader();
        uiDrawer.draw();
        ShaderManager.setTextShader();
        uiDrawer.drawText();
    }

    void updateFps(int fps) {
        uiDrawer.updateFps(fps);
    }

    void shutDown() {
        world.shutDownGeneratorExecutors();
    }
}