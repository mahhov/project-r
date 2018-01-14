package character;

import util.MathNumbers;

class Stamina {
    enum StaminaCost {
        JUMP(3), AIR_JUMP(90), JET(.11f), GLIDE(.07f), BOOST(5), THROW(0);

        final float value;

        StaminaCost(float value) {
            this.value = value;
        }
    }

    private float max, current, regen;
    private float maxReserve, currentReserve, regenReserve;

    Stamina(float max, float regen, float maxReserve, float regenReserve) {
        this.max = current = max;
        this.regen = regen;
        this.maxReserve = currentReserve = maxReserve;
        this.regenReserve = regenReserve;
    }

    void regen() {
        currentReserve = MathNumbers.min(currentReserve + regenReserve, maxReserve);

        float regenAmount = MathNumbers.min(regen, currentReserve, max - current);
        current = MathNumbers.min(current + regenAmount, max);
        currentReserve -= regenAmount;
    }

    void deplete(StaminaCost staminaCost) {
        current -= staminaCost.value;
    }

    boolean available(StaminaCost staminaCost) {
        return current >= staminaCost.value;
    }

    float percent() {
        return current / max;
    }

    float percentReserve() {
        return currentReserve / maxReserve;
    }
}