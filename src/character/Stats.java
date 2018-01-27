package character;

import item.gear.Property;

public class Stats {
    public enum StatType {
        RUN_ACC("Run Speed"), JUMP_ACC("Jump Power"), AIR_ACC("Air Speed"), JET_ACC("Jet Speed"),
        BOOST_ACC("Boost Speed"), GLIDE_ACC("Glide Speed"), GLIDE_DESCENT_ACC("Glide Descent Speed"),
        STAMINA("Stamina"), STAMINA_REGEN("Stamina Regen"), STAMINA_RESERVE("Stamina Reserve"), STAMINA_RESERVE_REGEN("Stamina Reserve Regen"),
        LIFE("Life"), LIFE_REGEN("Life Regen"), SHIELD("Shield"), SHIELD_REGEN("Shield Regen"), SHIELD_REGEN_DELAY("Shield Regen Delay");

        final String name;
        final int value;

        StatType(String name) {
            this.name = name;
            this.value = ordinal();
        }
    }

    private static final StatType[] STAT_TYPE_VALUES = StatType.values();

    private Stat stats[];
    private Experience experience;
    private Equipment equipment;

    Stats(float runAcc, float jumpAcc, float airAcc, float jetAcc, float boostAcc, float glideAcc, float glideDescentAcc, float stamina, float staminaRegen, float staminaReserve, float staminaReserveRegen, float life, float lifeRegen, float shield, float shieldRegen, float shieldRegenDelay) {
        stats = new Stat[getStatTypeCount()];

        stats[StatType.RUN_ACC.value] = new Stat(runAcc, .1f);
        stats[StatType.JUMP_ACC.value] = new Stat(jumpAcc, .1f);
        stats[StatType.AIR_ACC.value] = new Stat(airAcc, .1f);
        stats[StatType.JET_ACC.value] = new Stat(jetAcc, .1f);

        stats[StatType.BOOST_ACC.value] = new Stat(boostAcc, .1f);
        stats[StatType.GLIDE_ACC.value] = new Stat(glideAcc, .1f);
        stats[StatType.GLIDE_DESCENT_ACC.value] = new Stat(glideDescentAcc, .1f);

        stats[StatType.STAMINA.value] = new Stat(stamina, .1f);
        stats[StatType.STAMINA_REGEN.value] = new Stat(staminaRegen, .1f);
        stats[StatType.STAMINA_RESERVE.value] = new Stat(staminaReserve, .1f);
        stats[StatType.STAMINA_RESERVE_REGEN.value] = new Stat(staminaReserveRegen, .1f);

        stats[StatType.LIFE.value] = new Stat(life, .1f);
        stats[StatType.LIFE_REGEN.value] = new Stat(lifeRegen, .1f);
        stats[StatType.SHIELD.value] = new Stat(shield, .1f);
        stats[StatType.SHIELD_REGEN.value] = new Stat(shieldRegen, .1f);
        stats[StatType.SHIELD_REGEN_DELAY.value] = new Stat(shieldRegenDelay, .1f);
    }

    Stats(float life, float lifeRegen, float shield, float shieldRegen, int shieldRegenDelay) {
        stats = new Stat[getStatTypeCount()];

        stats[StatType.RUN_ACC.value] = null;
        stats[StatType.JUMP_ACC.value] = null;
        stats[StatType.AIR_ACC.value] = null;
        stats[StatType.JET_ACC.value] = null;

        stats[StatType.BOOST_ACC.value] = null;
        stats[StatType.GLIDE_ACC.value] = null;
        stats[StatType.GLIDE_DESCENT_ACC.value] = null;

        stats[StatType.STAMINA.value] = null;
        stats[StatType.STAMINA_REGEN.value] = null;
        stats[StatType.STAMINA_RESERVE.value] = null;
        stats[StatType.STAMINA_RESERVE_REGEN.value] = null;

        stats[StatType.LIFE.value] = new Stat(life, 0);
        stats[StatType.LIFE_REGEN.value] = new Stat(lifeRegen, 0);
        stats[StatType.SHIELD.value] = new Stat(shield, 0);
        stats[StatType.SHIELD_REGEN.value] = new Stat(shieldRegen, 0);
        stats[StatType.SHIELD_REGEN_DELAY.value] = new Stat(shieldRegenDelay, 0);
    }

    void setFactors(Experience experience, Equipment equipment) {
        this.experience = experience;
        this.equipment = equipment;
        update();
    }

    void update() {
        stats[StatType.RUN_ACC.value].update(experience.getSkillPoints(Experience.Skill.RUN_AIR_ACC), 0);
        stats[StatType.JUMP_ACC.value].update(experience.getSkillPoints(Experience.Skill.JUMP_ACC), 0);
        stats[StatType.AIR_ACC.value].update(experience.getSkillPoints(Experience.Skill.RUN_AIR_ACC), 0);
        stats[StatType.JET_ACC.value].update(experience.getSkillPoints(Experience.Skill.JET_ACC), 0);

        stats[StatType.BOOST_ACC.value].update(experience.getSkillPoints(Experience.Skill.BOOST_GLIDE_ACC), 0);
        stats[StatType.GLIDE_ACC.value].update(experience.getSkillPoints(Experience.Skill.BOOST_GLIDE_ACC), 0);

        stats[StatType.STAMINA.value].update(experience.getSkillPoints(Experience.Skill.STAMINA_RAW) + experience.getSkillPoints(Experience.Skill.STAMINA_STAMINA), equipment.getEquipmentBonus(Property.PropertyType.STAMINA_STAMINA));
        stats[StatType.STAMINA_REGEN.value].update(experience.getSkillPoints(Experience.Skill.STAMINA_REGEN) + experience.getSkillPoints(Experience.Skill.STAMINA_STAMINA), equipment.getEquipmentBonus(Property.PropertyType.STAMINA_STAMINA_REGEN));
        stats[StatType.STAMINA_RESERVE.value].update(experience.getSkillPoints(Experience.Skill.STAMINA_RAW) + experience.getSkillPoints(Experience.Skill.STAMINA_RESERVE), equipment.getEquipmentBonus(Property.PropertyType.STAMINA_RESERVE));
        stats[StatType.STAMINA_RESERVE_REGEN.value].update(experience.getSkillPoints(Experience.Skill.STAMINA_REGEN) + experience.getSkillPoints(Experience.Skill.STAMINA_RESERVE), equipment.getEquipmentBonus(Property.PropertyType.STAMINA_RESERVE_REGEN));

        stats[StatType.LIFE.value].update(experience.getSkillPoints(Experience.Skill.HEALTH_RAW) + experience.getSkillPoints(Experience.Skill.HEALTH_LIFE), equipment.getEquipmentBonus(Property.PropertyType.HEALTH_LIFE));
        stats[StatType.LIFE_REGEN.value].update(experience.getSkillPoints(Experience.Skill.HEALTH_REGEN) + experience.getSkillPoints(Experience.Skill.HEALTH_LIFE), equipment.getEquipmentBonus(Property.PropertyType.HEALTH_LIFE_REGEN));
        stats[StatType.SHIELD.value].update(experience.getSkillPoints(Experience.Skill.HEALTH_RAW) + experience.getSkillPoints(Experience.Skill.HEALTH_SHIELD), equipment.getEquipmentBonus(Property.PropertyType.HEALTH_SHIELD));
        stats[StatType.SHIELD_REGEN.value].update(experience.getSkillPoints(Experience.Skill.HEALTH_REGEN) + experience.getSkillPoints(Experience.Skill.HEALTH_SHIELD), equipment.getEquipmentBonus(Property.PropertyType.HEALTH_SHIELD_REGEN));
    }

    float getStat(StatType statType) {
        return stats[statType.value].value;
    }

    public String getText(StatType statType) {
        return statType.name + " " + getStat(statType);
    }

    public static int getStatTypeCount() {
        return STAT_TYPE_VALUES.length;
    }

    public static StatType getStatType(int i) {
        return STAT_TYPE_VALUES[i];
    }

    private class Stat {
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