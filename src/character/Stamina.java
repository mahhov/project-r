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

    private float stamina, reserve;
    private Stats stats;

    Stamina(Stats stats) {
        this.stats = stats;
        stamina = stats.stamina.getValue();
        reserve = stats.staminaReserve.getValue();
    }

    void regen() {
        reserve = MathNumbers.min(reserve + stats.staminaReserveRegen.getValue(), stats.staminaReserve.getValue());

        float regenAmount = MathNumbers.min(stats.staminaRegen.getValue(), reserve, stats.stamina.getValue() - stamina);
        stamina = MathNumbers.min(stamina + regenAmount, stats.stamina.getValue());
        reserve -= regenAmount;
    }

    void deplete(StaminaCost staminaCost) {
        stamina -= staminaCost.value;
    }

    boolean available(StaminaCost staminaCost) {
        return stamina >= staminaCost.value;
    }

    float percent() {
        return stamina / stats.stamina.getValue();
    }

    float percentReserve() {
        return reserve / stats.staminaReserve.getValue();
    }
}