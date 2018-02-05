package character.monster.behavior;

public class Timer {
    private int time;

    public void update() {
        if (time > 0)
            time--;
    }

    public void reset(int time) {
        this.time = time;
    }

    public boolean done() {
        return time == 0;
    }
}