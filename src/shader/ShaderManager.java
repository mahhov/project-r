package shader;

import static org.lwjgl.opengl.GL11.*;

public class ShaderManager {
    private static final ShaderProgram
            renderShader = new ShaderProgram("render"),
            uiShader = new ShaderProgram("ui"),
            textShader = new ShaderProgram("text");

    public static int getRenderShaderProgramId() {
        return renderShader.getProgramId();
    }

    public static void setRenderShader() {
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_BLEND);
        renderShader.bind();
    }

    public static int getUiShaderProgramId() {
        return uiShader.getProgramId();
    }

    public static void setUiShader() {
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        uiShader.bind();
    }

    public static int getTextShaderProgramId() {
        return textShader.getProgramId();
    }

    public static void setTextShader() {
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        textShader.bind();
    }
}