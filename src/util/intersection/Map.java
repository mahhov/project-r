package util.intersection;

import world.WorldElement;

public interface Map {
    boolean movable(int x, int y, int z);

    WorldElement hit(float x, float y, float z, float range);
}