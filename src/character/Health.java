package character;

import util.MathNumbers;

class Health {
    private float maxLife, currentLife, regenLife;
    private float maxShield, currentShield, regenShield;
    private int shieldRegenDelay, currentShieldRegenDelay;

    Health(float maxLife, float regenLife, float maxShield, float regenShield, int shieldRegenDelay) {
        this.maxLife = currentLife = maxLife;
        this.regenLife = regenLife;
        this.maxShield = currentShield = maxShield;
        this.regenShield = regenShield;
        this.shieldRegenDelay = shieldRegenDelay;
    }

    void regen() {
        currentLife = MathNumbers.min(currentLife + regenLife, maxLife);
        if (currentShieldRegenDelay > 0)
            currentShieldRegenDelay--;
        else
            currentShield = MathNumbers.min(currentShield + regenShield, maxShield);
    }

    void deplete(float amount) {
        currentShieldRegenDelay = shieldRegenDelay;
        if (amount < currentShield)
            currentShield -= amount;
        else {
            amount -= currentShield;
            currentShield = 0;
            currentLife = MathNumbers.max(currentLife - amount, 0);
        }
    }

    boolean depleted() {
        return currentLife == 0;
    }

    float percentLife() {
        return currentLife / maxLife;
    }

    float percentShield() {
        return currentShield / maxShield;
    }
}