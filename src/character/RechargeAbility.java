package character;

import util.MathNumbers;

class RechargeAbility {
    private float maxCharge, curCharge, depleteChargeRate, regenChargeRate;

    public RechargeAbility(float maxCharge, float depleteChargeRate, float regenChargeRate) {
        this.maxCharge = curCharge = maxCharge;
        this.depleteChargeRate = depleteChargeRate;
        this.regenChargeRate = regenChargeRate;
    }

    void regen() {
        curCharge = MathNumbers.min(curCharge + regenChargeRate, maxCharge);
    }

    void deplete() {
        curCharge = MathNumbers.max(curCharge - depleteChargeRate, 0);
    }

    boolean ready() {
        return curCharge > depleteChargeRate;
    }
}
