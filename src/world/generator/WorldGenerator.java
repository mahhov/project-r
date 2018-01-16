package world.generator;

public interface WorldGenerator {
    byte[][][] generate(int startX, int startY, int startZ);
}