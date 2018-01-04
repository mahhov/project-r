package character;

class AbilityTimer {
    private int cooldownTime, durationTime, curTime;

    AbilityTimer(int cooldownTime, int durationTime) {
        this.cooldownTime = cooldownTime;
        this.durationTime = -durationTime;
    }

    void update() {
        if (curTime != 0)
            curTime--;
        if (curTime == durationTime)
            curTime = cooldownTime;
    }

    void activate() {
        curTime = -1;
    }

    boolean ready() {
        return curTime == 0;
    }

    boolean active() {
        return curTime < 0;
    }

    float percent() {
        return 1f * curTime / cooldownTime;
    }
}
