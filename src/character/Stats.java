package character;

class Stats {
    Stat runAcc, jumpAcc, airAcc, jetAcc; // todo private & getters
    Stat boostAcc, glideAcc, glideDescentAcc;
    Stat stamina, staminaRegen, staminaReserve, staminaReserveRegen;
    Stat life, lifeRegen, shield, shieldRegen, shieldRegenDelay;
    private Experience experience;
    private Equipment equipment;

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

    void setFactors(Experience experience, Equipment equipment) {
        this.experience = experience;
        this.equipment = equipment;
    }

    void update() {
        runAcc.update(experience.getSkillPoints(Experience.Skill.RUN_AIR_ACC), 0);
        jumpAcc.update(experience.getSkillPoints(Experience.Skill.JUMP_ACC), 0);
        airAcc.update(experience.getSkillPoints(Experience.Skill.RUN_AIR_ACC), 0);
        jetAcc.update(experience.getSkillPoints(Experience.Skill.JET_ACC), 0);

        boostAcc.update(experience.getSkillPoints(Experience.Skill.BOOST_GLIDE_ACC), 0);
        glideAcc.update(experience.getSkillPoints(Experience.Skill.BOOST_GLIDE_ACC), 0);

        stamina.update(experience.getSkillPoints(Experience.Skill.STAMINA_RAW) + experience.getSkillPoints(Experience.Skill.STAMINA_STAMINA), equipment.getEquipmentBonus(Equipment.PropertyType.STAMINA_STAMINA));
        staminaRegen.update(experience.getSkillPoints(Experience.Skill.STAMINA_REGEN) + experience.getSkillPoints(Experience.Skill.STAMINA_STAMINA), equipment.getEquipmentBonus(Equipment.PropertyType.STAMINA_STAMINA_REGEN));
        staminaReserve.update(experience.getSkillPoints(Experience.Skill.STAMINA_RAW) + experience.getSkillPoints(Experience.Skill.STAMINA_RESERVE), equipment.getEquipmentBonus(Equipment.PropertyType.STAMINA_RESERVE));
        staminaReserveRegen.update(experience.getSkillPoints(Experience.Skill.STAMINA_REGEN) + experience.getSkillPoints(Experience.Skill.STAMINA_RESERVE), equipment.getEquipmentBonus(Equipment.PropertyType.STAMINA_RESERVE_REGEN));

        life.update(experience.getSkillPoints(Experience.Skill.HEALTH_RAW) + experience.getSkillPoints(Experience.Skill.HEALTH_LIFE), equipment.getEquipmentBonus(Equipment.PropertyType.HEALTH_LIFE));
        lifeRegen.update(experience.getSkillPoints(Experience.Skill.HEALTH_REGEN) + experience.getSkillPoints(Experience.Skill.HEALTH_LIFE), equipment.getEquipmentBonus(Equipment.PropertyType.HEALTH_LIFE_REGEN));
        shield.update(experience.getSkillPoints(Experience.Skill.HEALTH_RAW) + experience.getSkillPoints(Experience.Skill.HEALTH_SHIELD), equipment.getEquipmentBonus(Equipment.PropertyType.HEALTH_SHIELD));
        shieldRegen.update(experience.getSkillPoints(Experience.Skill.HEALTH_REGEN) + experience.getSkillPoints(Experience.Skill.HEALTH_SHIELD), equipment.getEquipmentBonus(Equipment.PropertyType.HEALTH_SHIELD_REGEN));
    }

    class Stat { // todo private
        private final float defaultValue, growthPerSkillPoint;
        private float value;

        private Stat(float defaultValue, float growthPerSkillPoint) {
            value = this.defaultValue = defaultValue;
            this.growthPerSkillPoint = growthPerSkillPoint;
        }

        void update(int skillPoints, int equipmentBonus) {
            value = (defaultValue + equipmentBonus) * (1 + skillPoints * growthPerSkillPoint);
        }

        float getValue() {
            return value;
        }
    }
}