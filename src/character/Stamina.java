package character;

import util.MathNumbers;

class Stamina {
    static final float JUMP = 3, AIR_JUMP = 9, JET = .11f, GLIDE = .07f, BOOST = 5;

    private float max, current, regen;
    private float maxReserve, currentReserve, regenReserve;

    Stamina(float max, float regen, float maxReserve, float regenReserve) {
        this.max = current = max;
        this.regen = regen;
        this.maxReserve = currentReserve = maxReserve;
        this.regenReserve = regenReserve;
    }

    void regen() {
        if (currentReserve < maxReserve)
            currentReserve = MathNumbers.min(currentReserve + regenReserve, maxReserve);
        if (current < max) {
            float regenAmount = MathNumbers.min(regen, currentReserve);
            current = MathNumbers.min(current + regenAmount, max);
            currentReserve -= regenAmount;
        }
    }

    void deplete(float amount) {
        current -= amount;
    }

    boolean available(float amount) {
        return current >= amount;
    }

    float percent() {
        return current / max;
    }

    float percentReserve() {
        return currentReserve / maxReserve;
    }
}