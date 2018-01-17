package character;

import util.MathNumbers;

class Health {
    private float life, shield;
    private float shieldRegenDelay;
    private Stats stats;

    Health(Stats stats) {
        this.stats = stats;
        life = stats.getStat(Stats.StatType.LIFE);
        shield = stats.getStat(Stats.StatType.SHIELD);
    }

    void regen() {
        life = MathNumbers.min(life + stats.getStat(Stats.StatType.LIFE_REGEN), stats.getStat(Stats.StatType.LIFE));
        if (shieldRegenDelay > 0)
            shieldRegenDelay--;
        else
            shield = MathNumbers.min(shield + stats.getStat(Stats.StatType.SHIELD_REGEN), stats.getStat(Stats.StatType.SHIELD));
    }

    void deplete(float amount) {
        shieldRegenDelay = stats.getStat(Stats.StatType.SHIELD_REGEN_DELAY);
        if (amount < shield)
            shield -= amount;
        else {
            amount -= shield;
            shield = 0;
            life = MathNumbers.max(life - amount, 0);
        }
    }

    boolean depleted() {
        return life == 0;
    }

    float percentLife() {
        return life / stats.getStat(Stats.StatType.LIFE);
    }

    float percentShield() {
        return shield / stats.getStat(Stats.StatType.SHIELD);
    }
}