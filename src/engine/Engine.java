package engine;

import control.KeyControl;
import control.MouseButtonControl;
import control.MousePosControl;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Engine {
    private static final int WINDOW_SIZE = 1000;
    public static final int SCALE = 50, SCALE_Z = 16;

    private static final long NANOSECONDS_IN__SECOND = 1000000000L;
    private long window;

    private Game game;

    private Engine() {
        initLwjgl();
        KeyControl keyControl = new KeyControl(window);
        MousePosControl mousePosControl = new MousePosControl(window);
        MouseButtonControl mouseButtonControl = new MouseButtonControl(window);
        game = new Game(keyControl, mousePosControl, mouseButtonControl);
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

        glDepthFunc(GL_LESS);
        glEnable(GL_CULL_FACE);
        glEnable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    private void destroyLwjgl() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void run() {
        int engineFrame = 0;
        long beginTime = 0, endTime;

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            game.loop();

            glfwSwapBuffers(window);
            glfwPollEvents();

            engineFrame++;
            endTime = System.nanoTime() + 1;
            if (endTime - beginTime > NANOSECONDS_IN__SECOND) {
                game.updateFps(engineFrame);
                engineFrame = 0;
                beginTime = endTime;
            }
        }

        game.shutDown();
        destroyLwjgl();
    }

    public static void main(String[] args) {
        new Engine().run();
    }
}

// todo
// ~~ ordered ~~
// weapon crafting

// ~~ high priority ~~
// crafting enchant level / exp requirement
// crafting help text on posible properties and # of glows consumed
// weapon equipment & crafting (modules, weapon max weight, module base weight .5 + .25 per property, module properties random 1-4)
// combat
// enemies
// progression
// instances
// harvesting
// crafting (best gear: 1. craftable, 2. choices of pros/cons, 3. materials required rare drops from touch monsters)
// replace string concat with builder
// big monsters / gloob & doom / boss telegraphed abilities
// supplies / consumables
// harvesting
// ui log scrolling
// redo ui

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
// particles