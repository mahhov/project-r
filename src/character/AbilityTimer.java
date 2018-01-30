package character;

class AbilityTimer {
    // need to call update *time* times after activating / resetting cooldown before it's inactive / cooldown ready
    private int cooldownTime, durationTime, curTime;

    AbilityTimer(int cooldownTime, int durationTime) {
        this.cooldownTime = cooldownTime;
        this.durationTime = -durationTime - 1;
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

    boolean justActiveated() {
        return curTime == -1;
    }
}