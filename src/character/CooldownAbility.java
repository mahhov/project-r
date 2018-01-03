package character;

class CooldownAbility {
    private int maxTime, curTime;

    CooldownAbility(int time) {
        maxTime = time;
    }

    void update() {
        if (curTime > 0)
            curTime--;
    }

    void reset() {
        curTime = maxTime;
    }

    boolean ready() {
        return curTime == 0;
    }
}
