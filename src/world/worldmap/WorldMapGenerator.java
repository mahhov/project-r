package world.worldmap;

public interface WorldMapGenerator {
    WorldMap generate(int startX, int startY, int startZ);
}