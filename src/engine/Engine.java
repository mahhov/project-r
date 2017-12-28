package engine;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import world.World;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Engine {
    public static final boolean DEBUG_PRINT_DRAW_COUNT = false;

    private static final long NANOSECONDS_IN__SECOND = 1000000000L;
    private long window;
    private ShaderProgram shaderProgram;
    private Camera camera;
    private Controller controller;
    private World world;

    Engine() {
        initLwjgl();
        camera = new Camera(shaderProgram.getProgramId());
        controller = new Controller(window);
        world = new World(32, 32, 16);
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
        window = glfwCreateWindow(300, 300, "title", NULL, NULL);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);

        //        glFrontFace(GL_CCW);
        //        glEnable(GL_CULL_FACE);
        //        glCullFace(GL_BACK);

        shaderProgram = new ShaderProgram();
        shaderProgram.bind();
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

        controller = new Controller(window);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            camera.update(controller);

            world.update();
            world.draw();

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