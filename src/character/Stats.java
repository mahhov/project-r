package character;

class Stats {
    final Stat runAcc, jumpAcc, airAcc, jetAcc;
    final Stat boostAcc, glideAcc, glideDescentAcc;
    final Stat stamina, staminaRegen, staminaReserve, staminaReserveRegen;
    final Stat life, lifeRegen, shield, shieldRegen, shieldRegenDelay;

    Stats(float runAcc, float jumpAcc, float airAcc, float jetAcc, float boostAcc, float glideAcc, float glideDescentAcc, float stamina, float staminaRegen, float staminaReserve, float staminaReserveRegen, float life, float lifeRegen, float shield, float shieldRegen, float shieldRegenDelay) {
        this.runAcc = new Stat(runAcc, .1f);
        this.jumpAcc = new Stat(jumpAcc, .1f);
        this.airAcc = new Stat(airAcc, .1f);
        this.jetAcc = new Stat(jetAcc, .1f);

        this.boostAcc = new Stat(boostAcc, .1f);
        this.glideAcc = new Stat(glideAcc, .1f);
        this.glideDescentAcc = new Stat(glideDescentAcc, .1f);

        this.stamina = new Stat(stamina, .1f);
        this.staminaRegen = new Stat(staminaRegen, .1f);
        this.staminaReserve = new Stat(staminaReserve, .1f);
        this.staminaReserveRegen = new Stat(staminaReserveRegen, .1f);

        this.life = new Stat(life, .1f);
        this.lifeRegen = new Stat(lifeRegen, .1f);
        this.shield = new Stat(shield, .1f);
        this.shieldRegen = new Stat(shieldRegen, .1f);
        this.shieldRegenDelay = new Stat(shieldRegenDelay, .1f);
    }

    Stats(float life, float lifeRegen, float shield, float shieldRegen, int shieldRegenDelay) {
        this.runAcc = null;
        this.jumpAcc = null;
        this.airAcc = null;
        this.jetAcc = null;

        this.boostAcc = null;
        this.glideAcc = null;
        this.glideDescentAcc = null;

        this.stamina = null;
        this.staminaRegen = null;
        this.staminaReserve = null;
        this.staminaReserveRegen = null;

        this.life = new Stat(life, .1f);
        this.lifeRegen = new Stat(lifeRegen, .1f);
        this.shield = new Stat(shield, .1f);
        this.shieldRegen = new Stat(shieldRegen, .1f);
        this.shieldRegenDelay = new Stat(shieldRegenDelay, .1f);
    }

    class Stat {
        private final float defaultValue, growthPerSkillPoint;
        private float value;

        private Stat(float defaultValue, float growthPerSkillPoint) {
            value = this.defaultValue = defaultValue;
            this.growthPerSkillPoint = growthPerSkillPoint;
        }

        void update(int skillPoints) {
            value = defaultValue * (1 + skillPoints * growthPerSkillPoint);
        }

        float getValue() {
            return value;
        }
    }
}