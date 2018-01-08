package engine;

import camera.Camera;
import character.Human;
import control.KeyControl;
import control.MouseButtonControl;
import control.MousePosControl;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import shader.ShaderManager;
import world.World;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Engine {
    private static final int WINDOW_SIZE = 400;
    public static final int SCALE = 8;

    private static final long NANOSECONDS_IN__SECOND = 1000000000L;
    private long window;
    private Camera camera;
    private Human human;
    private UiDrawer uiDrawer;
    private KeyControl keyControl;
    private MousePosControl mousePosControl;
    private MouseButtonControl mouseButtonControl;
    private World world;

    private Engine() {
        initLwjgl();
        camera = new Camera(ShaderManager.getRenderShaderProgramId());
        keyControl = new KeyControl(window);
        mousePosControl = new MousePosControl(window);
        mouseButtonControl = new MouseButtonControl(window);
        world = new World(64 * SCALE, 64 * SCALE, 16 * SCALE, camera);
        human = new Human(32 * Engine.SCALE, 0, 8 * Engine.SCALE, 0, 0, world.getIntersectionMover(), world.getIntersectionPicker(), keyControl, mousePosControl, mouseButtonControl);
        world.setHuman(human);
        world.addRandomMonsters(100);
        uiDrawer = new UiDrawer(human);
        ShaderManager.setUiShader();
        camera.setFollow(human);
    }

    private void initLwjgl() {
        System.out.println("LWJGL " + Version.getVersion());

        GLFWErrorCallback.createPrint(System.err).set();
        glfwInit();
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        window = glfwCreateWindow(WINDOW_SIZE, WINDOW_SIZE, "Project R", NULL, NULL);

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);

        glEnable(GL_CULL_FACE);

        ShaderManager.setRenderShader();
    }

    private void destroyLwjgl() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void run() {
        int drawFrame = 0, engineFrame = 0;
        long beginTime = 0, endTime;

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            ShaderManager.setRenderShader();
            camera.update(keyControl);
            world.setCameraCoordinate(camera.getWorldCoordinate());
            world.update();
            world.draw();

            ShaderManager.setUiShader();
            uiDrawer.draw();

            glfwSwapBuffers(window);
            glfwPollEvents();

            engineFrame++;
            endTime = System.nanoTime() + 1;
            if (endTime - beginTime > NANOSECONDS_IN__SECOND) {
                glfwSetWindowTitle(window, "fps " + engineFrame);
                drawFrame = 0;
                engineFrame = 0;
                beginTime = endTime;
            }
        }
    }

    public static void main(String[] args) {
        new Engine().run();
    }
}

// todo
// ~~ high priority ~~
// combat
// enemies
// particles
// progression
// instances
// inventory
// harvesting
// crafting

// ~~ low priority ~~
// camera culling
// multi-light support
// multithread world chunk fill
// blur distant
// polygon outline
// more efficient is(draw/world)empty tracking
// shadows
// investigate if should retain STATIC_DRAW
// support cubes of different colors in same CubeInstancedFaces
// multi thread chunk loading