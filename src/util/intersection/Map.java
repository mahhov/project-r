package util.intersection;

import world.WorldElement;

public interface Map {
    boolean moveable(int x, int y, int z);

    WorldElement hit(float x, float y, float z, float range);

    int width();

    int length();

    int height();
}