package character.monster.motion;

public class Timer {
    private int time;

    void update() {
        if (time > 0)
            time--;
    }

    void reset(int time) {
        this.time = time;
    }

    boolean done() {
        return time == 0;
    }
}