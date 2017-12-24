package world;

import util.LList;
import world.particle.Particle;

public class World {
    private static final int CHUNK_SIZE = 16;
    private WorldChunk[][][] worldChunks;
    private Character character;
    private LList<Particle> particles;

    public World(int width, int length, int height) {
        worldChunks = new WorldChunk[width / CHUNK_SIZE][length / CHUNK_SIZE][height / CHUNK_SIZE];
        character = new Character();
        particles = new LList<>();
    }
    
    public void update(){
        
    }
    
    public void draw(){}
}
