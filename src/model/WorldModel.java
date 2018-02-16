package model;

import world.WorldElement;

abstract class WorldModel extends Model {
    WorldElement worldElement;

    WorldModel(WorldElement worldElement, int segmentCount) {
        super(segmentCount);
        this.worldElement = worldElement;
    }
}