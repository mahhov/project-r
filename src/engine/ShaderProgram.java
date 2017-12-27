package engine;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
    private int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    public ShaderProgram() {
        programId = glCreateProgram();
        if (programId == 0)
            throw new RuntimeException("Could not create ShaderProgram");

        vertexShaderId = createShader("shaders/VertexShader.vs", GL_VERTEX_SHADER);
        fragmentShaderId = createShader("shaders/FragmentShader.fs", GL_FRAGMENT_SHADER);

        link();
    }

    private int createShader(String shaderFile, int shaderType) {
        String shaderCode = null;
        try {
            shaderCode = new String(Files.readAllBytes(Paths.get(ShaderProgram.class.getResource(shaderFile).toURI())));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't read shader file " + shaderFile);
        }

        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0)
            throw new RuntimeException("Error creating shader. Type: " + shaderType);

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0)
            throw new RuntimeException("Error compiling ShaderProgram code: " + glGetShaderInfoLog(shaderId, 1024));

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    private void link() {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0)
            throw new RuntimeException("Error linking ShaderProgram code: " + glGetProgramInfoLog(programId, 1024));

        if (vertexShaderId != 0)
            glDetachShader(programId, vertexShaderId);
        if (fragmentShaderId != 0)
            glDetachShader(programId, fragmentShaderId);
    }

    void bind() {
        glUseProgram(programId);
    }

    void unbind() {
        glUseProgram(0);
    }

    void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }

    int getProgramId() {
        return programId;
    }
}