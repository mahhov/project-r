package engine;

import camera.Camera;
import control.KeyControl;
import control.MousePosControl;
import engine.shader.ShaderManager;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import world.Character;
import world.World;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Engine {
    public static final int SCALE = 8;

    private static final long NANOSECONDS_IN__SECOND = 1000000000L;
    private long window;
    private Camera camera;
    private Character character;
    private UiDrawer uiDrawer;
    private KeyControl keyControl;
    private MousePosControl mousePosControl;
    private World world;

    private Engine() {
        initLwjgl();
        camera = new Camera(ShaderManager.getRenderShaderProgramId());
        keyControl = new KeyControl(window);
        mousePosControl = new MousePosControl(window);
        world = new World(64 * SCALE, 64 * SCALE, 16 * SCALE);
        character = new Character(32 * Engine.SCALE, 0, 8 * Engine.SCALE, 0, 0, world);
        ShaderManager.setUiShader();
        uiDrawer = new UiDrawer(character);
        world.addWorldElement(character);
        camera.setFollow(character);
    }

    private void initLwjgl() {
        System.out.println("LWJGL " + Version.getVersion());

        //        GLFWErrorCallback.createPrint(System.err).set();
        //
        //        if (!glfwInit())
        //            throw new IllegalStateException("Unable to initialize GLFW");
        //        glfwDefaultWindowHints();
        //
        //        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        //        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        //        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        //        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        //
        //        window = glfwCreateWindow(300, 300, "Realm", NULL, NULL);
        //        if (window == NULL)
        //            throw new RuntimeException("Failed to create the GLFW window");
        //
        //        try (MemoryStack stack = stackPush()) {
        //            IntBuffer pWidth = stack.mallocInt(1);
        //            IntBuffer pHeight = stack.mallocInt(1);
        //            glfwGetWindowSize(window, pWidth, pHeight);
        //            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        //            glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        //        }
        //
        //        glfwMakeContextCurrent(window);
        //        glfwSwapInterval(1);
        //
        //        glfwShowWindow(window);
        //
        //        GL.createCapabilities();
        //        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        GLFWErrorCallback.createPrint(System.err).set();
        glfwInit();
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        window = glfwCreateWindow(800, 800, "Project R", NULL, NULL);

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

        keyControl = new KeyControl(window);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            character.updateControls(keyControl, mousePosControl);

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
                System.out.println(engineFrame);
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
// ui
// character movement
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