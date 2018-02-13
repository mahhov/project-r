package game;

import camera.Camera;
import character.Human;
import control.KeyControl;
import control.MouseButtonControl;
import control.MousePosControl;
import engine.Engine;
import engine.EngineRunnable;
import map.Map;
import shader.ShaderManager;
import ui.UiDrawer;
import world.World;

public class Game implements EngineRunnable {
    private KeyControl keyControl;
    private MousePosControl mousePosControl;
    private MouseButtonControl mouseButtonControl;

    private Map map;
    private World world;
    private Human human;
    private Camera camera;
    private UiDrawer uiDrawer;

    @Override
    public void init(KeyControl keyControl, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl) {
        this.keyControl = keyControl;
        this.mousePosControl = mousePosControl;
        this.mouseButtonControl = mouseButtonControl;

        ShaderManager.setRenderShader();
        camera = new Camera(ShaderManager.getRenderShaderProgramId(), 6, 1); // todo make static final
        map = new Map(this);
        loadMap(0);
    }

    public void loadMap(int selected) {
        mousePosControl.lock();
        if (world != null)
            world.shutDownGeneratorExecutors();
        world = new World(64 * Engine.SCALE, 64 * Engine.SCALE, 16 * Engine.SCALE_Z, camera);
        human = new Human(32 * Engine.SCALE, 1, 15 * Engine.SCALE_Z, 0, 0, world.getIntersectionMover(), world.getIntersectionPicker(), keyControl, mousePosControl, mouseButtonControl);
        world.setHuman(human);
        ShaderManager.setTextShader();
        uiDrawer = new UiDrawer(human, map, keyControl, mousePosControl, mouseButtonControl);
        camera.setFollow(human);
    }

    @Override
    public void loop() {
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

        mouseButtonControl.next();
    }

    @Override
    public void updateFps(int fps) {
        uiDrawer.updateFps(fps);
    }

    @Override
    public void shutDown() {
        world.shutDownGeneratorExecutors();
    }

    public static void main(String[] args) {
        new Engine(new Game());
    }
}