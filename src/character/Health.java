package character;

import util.MathNumbers;

class Health {
    private float life, shield;
    private float shieldRegenDelay;
    private Stats stats;

    Health(Stats stats) {
        this.stats = stats;
        life = stats.life.getValue();
        shield = stats.shield.getValue();
    }

    void regen() {
        life = MathNumbers.min(life + stats.lifeRegen.getValue(), stats.life.getValue());
        if (shieldRegenDelay > 0)
            shieldRegenDelay--;
        else
            shield = MathNumbers.min(shield + stats.shieldRegen.getValue(), stats.shield.getValue());
    }

    void deplete(float amount) {
        shieldRegenDelay = stats.shieldRegenDelay.getValue();
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
        return life / stats.life.getValue();
    }

    float percentShield() {
        return shield / stats.shield.getValue();
    }
}