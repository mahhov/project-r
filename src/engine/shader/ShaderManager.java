package engine.shader;

public class ShaderManager {
    private static final ShaderProgram renderShader = new ShaderProgram("render"), uiShader = new ShaderProgram("ui");

    public static void setRenderShader() {
        renderShader.bind();
    }

    public static int getRenderShaderProgramId() {
        return renderShader.getProgramId();
    }

    public static void setUiShader() {
        uiShader.bind();
    }
}