//package world.particle;
//
//import geometry.Coordinate;
//
//public class Particle {
//    private Coordinate position;
//    private Coordinate velocity;
//    private long time;
//
//    public Particle(Coordinate position, Coordinate velocity, long time) {
//        this.position = position;
//        this.velocity = velocity;
//        this.time = time;
//    }
//
//    public boolean update() {
//        position.add(velocity);
//        return time-- <= 0;
//    }
//}
