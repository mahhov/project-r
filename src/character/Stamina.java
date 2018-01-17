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
        stamina = stats.getStat(Stats.StatType.STAMINA);
        reserve = stats.getStat(Stats.StatType.STAMINA_RESERVE);
    }

    void regen() {
        reserve = MathNumbers.min(reserve + stats.getStat(Stats.StatType.STAMINA_RESERVE_REGEN), stats.getStat(Stats.StatType.STAMINA_RESERVE));

        float regenAmount = MathNumbers.min(stats.getStat(Stats.StatType.STAMINA_REGEN), reserve, stats.getStat(Stats.StatType.STAMINA) - stamina);
        stamina = MathNumbers.min(stamina + regenAmount, stats.getStat(Stats.StatType.STAMINA));
        reserve -= regenAmount;
    }

    void deplete(StaminaCost staminaCost) {
        stamina -= staminaCost.value;
    }

    boolean available(StaminaCost staminaCost) {
        return stamina >= staminaCost.value;
    }

    float percent() {
        return stamina / stats.getStat(Stats.StatType.STAMINA);
    }

    float percentReserve() {
        return reserve / stats.getStat(Stats.StatType.STAMINA_RESERVE);
    }
}