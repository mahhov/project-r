package world;

public interface WorldElement {
    boolean update(World world);

    void takeDamage(float amount);

    void draw();

    float getX();

    float getY();

    float getZ();

    float getTheta();

    float getSize();

    int getId();
}