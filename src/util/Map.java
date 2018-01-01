package util;

public interface Map {
    boolean moveable(int x, int y, int z);

    int width();

    int length();
    
    int height();
}