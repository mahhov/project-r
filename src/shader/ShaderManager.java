package shader;

import static org.lwjgl.opengl.GL11.*;

public class ShaderManager {
    private static final ShaderProgram
            renderShader = new ShaderProgram("render"),
            uiShader = new ShaderProgram("ui"),
            textShader = new ShaderProgram("text");

    static {
        glDepthFunc(GL_LESS);
        glEnable(GL_CULL_FACE);
        glEnable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
        
        glClearColor(.5f, .8f, 1, 1);
    }

    public static int getRenderShaderProgramId() {
        return renderShader.getProgramId();
    }

    public static void setRenderShader() {
        glEnable(GL_DEPTH_TEST);
        renderShader.bind();
    }

    public static int getUiShaderProgramId() {
        return uiShader.getProgramId();
    }

    public static void setUiShader() {
        glDisable(GL_DEPTH_TEST);
        uiShader.bind();
    }

    public static int getTextShaderProgramId() {
        return textShader.getProgramId();
    }

    public static void setTextShader() {
        glDisable(GL_DEPTH_TEST);
        textShader.bind();
    }
}