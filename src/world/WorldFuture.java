package world;

import org.lwjgl.system.MemoryUtil;
import util.LList;
import world.particle.Particle;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.memFree;

public class WorldFuture {
    private static final int CHUNK_SIZE = 16;
    private WorldChunk[][][] worldChunks;
    private Character character;
    private LList<Particle> particles;

    private int[][] flatMap;
    
    public WorldFuture(int width, int length, int height) {
        worldChunks = new WorldChunk[width / CHUNK_SIZE][length / CHUNK_SIZE][height / CHUNK_SIZE];
        character = new Character();
        particles = new LList<>();

        flatMap = new int[20][20];
        for (int x = 0; x < flatMap.length; x++)
            for (int y = 0; y < flatMap[0].length; y++)
                flatMap[x][y] = (int) (Math.random() * 5);
    }

    public void update() {

    }

    public void draw() {
        for (int x = 0; x < flatMap.length; x++)
            for (int y = 0; y < flatMap[0].length; y++)
                drawX(x, y, flatMap[x][y]);
    }

    private void drawX(int x, int y, int z) {
        if (z < 4)
            return;

        float width = 2f / flatMap.length, height = 2f / flatMap[0].length;
        float leftX = (x * width) - 1, rightX = leftX + width;
        float topY = (y * height) - 1, bottomY = topY + height;

        float[] vertices = new float[] {
                leftX, topY, 0.0f,
                leftX, bottomY, 0.0f,
                rightX, bottomY, 0.0f,
                rightX, topY, 0.0f,
        };
        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
        verticesBuffer.put(vertices).flip();

        int vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        int vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glEnableVertexAttribArray(0);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 4);
        glDisableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        memFree(verticesBuffer);
        glDeleteBuffers(vboId);
        glDeleteVertexArrays(vaoId);
    }
}
