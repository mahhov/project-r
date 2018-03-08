package world.element;

import shape.CubeInstancedFaces;
import world.World;
import world.WorldLightElement;

public abstract class Element implements WorldLightElement {
    CubeInstancedFaces cubeInstancedFaces;

    Element() {
    }

    public void connectWorld(World world, CubeInstancedFaces cubeInstancedFaces) {
        this.cubeInstancedFaces = cubeInstancedFaces;
    }

    // todo remove or have default bodies for update & draw
    @Override
    public boolean update(World world) {
        return false;
    }

    @Override
    public void draw() {
    }
}

// todo, create static element type and static element list in world to track elements that do not update or change draw