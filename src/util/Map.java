package util;

public interface Map {
    boolean moveable(int x, int y, int z);

    boolean hit(float x, float y, float z, float range);

    int width();

    int length();

    int height();
}