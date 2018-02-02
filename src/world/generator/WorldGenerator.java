package world.generator;

public interface WorldGenerator {
    WorldMap generate(int startX, int startY, int startZ);
}