package world.particle;

import shape.CubeInstancedFaces;
import util.math.MathRandom;
import world.World;
import world.WorldLightElement;

public abstract class Particle implements WorldLightElement {
    private float x, y, z;
    float vx, vy, vz;
    private int time;
    private float color[], size;
    private float theta, thetaZ;

    private CubeInstancedFaces cubeInstancedFaces;

    Particle(float x, float y, float z, float vx, float vy, float vz, int time, float[] color, float size) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.time = MathRandom.random(time / 2, time);
        this.color = color;
        this.size = size;
        theta = MathRandom.randomAngle();
        thetaZ = MathRandom.randomAngle();
    }

    public void connectWorld(World world, CubeInstancedFaces cubeInstancedFaces) {
        this.cubeInstancedFaces = cubeInstancedFaces;
    }

    @Override
    public boolean update(World world) {
        x += vx;
        y += vy;
        z += vz;
        return time-- <= 0;
    }

    @Override
    public void draw() {
        cubeInstancedFaces.add(x, z, -y, theta, thetaZ, size, color);
    }
}
