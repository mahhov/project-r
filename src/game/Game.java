package game;

import camera.Camera;
import character.Human;
import control.Controls;
import engine.Engine;
import engine.EngineRunnable;
import map.Map;
import model.ModelGenerator;
import shader.ShaderManager;
import ui.UiDrawerGame;
import world.World;

public class Game implements EngineRunnable {
    private Controls controls;

    private Map map;
    private World world;
    private Human human;
    private Camera camera;
    private UiDrawerGame uiDrawer;

    @Override
    public void init(Controls controls) {
        this.controls = controls;
        ShaderManager.setRenderShader();
        camera = new Camera(ShaderManager.getRenderShaderProgramId(), 6, 1); // todo make static final
        map = new Map(this);
        loadMap(0);
    }

    @Override
    public void printHelp() {
    }

    public void loadMap(int selected) {
        controls.mousePosControl.lock();
        if (world != null)
            world.shutDownGeneratorExecutors();
        world = new World(64 * Engine.SCALE, 64 * Engine.SCALE, 16 * Engine.SCALE_Z, camera);
        human = new Human(32 * Engine.SCALE, 1, 15 * Engine.SCALE_Z, 0, 0, world.getIntersectionMover(), world.getIntersectionPicker(), controls.keyControl, controls.mousePosControl, controls.mouseButtonControl);
        world.setHuman(human);
        ShaderManager.setTextShader();
        uiDrawer = new UiDrawerGame(human, map, controls.keyControl, controls.mousePosControl, controls.mouseButtonControl);
        camera.setFollow(human);
    }

    @Override
    public void loop() {
        ShaderManager.setRenderShader();
        camera.update(controls.keyControl);
        world.setCameraCoordinate(camera.getWorldCoordinate());
        world.update();
        world.draw();

        uiDrawer.update();
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
        world.shutDownGeneratorExecutors();
    }

    public static void main(String[] args) {
        ModelGenerator.generate();
        new Engine(new Game());
    }
}