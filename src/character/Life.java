package character;

import util.MathNumbers;

class Life {
    private float maxLife, currentLife, regenLife;
    private float maxShield, currentShield, regenShield;

    Life(float maxLife, float regenLife, float maxShield, float regenShield) {
        this.maxLife = currentLife = maxLife;
        this.regenLife = regenLife;
        this.maxShield = currentShield = maxShield;
        this.regenShield = regenShield;
    }

    void regen() {
        currentLife = MathNumbers.min(currentLife + regenLife, maxLife);
        currentShield = MathNumbers.min(currentShield + regenShield, maxShield);
    }

    void deplete(float amount) {
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
