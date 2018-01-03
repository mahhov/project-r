package character;

class ChargesAbility {
    private int maxCharges, curCharges;

    ChargesAbility(int charges) {
        maxCharges = curCharges = charges;
    }

    void resetCharges() {
        curCharges = maxCharges;
    }

    void useCharge() {
        curCharges--;
    }

    boolean ready() {
        return curCharges > 0;
    }
}
