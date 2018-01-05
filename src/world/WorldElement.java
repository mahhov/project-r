package world;

public interface WorldElement {
    void update(World world);

    boolean takeDamage(float amount);

    void draw();

    float getX();

    float getY();

    float getZ();

    float getSize();
}