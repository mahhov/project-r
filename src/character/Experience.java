package character;

import util.MathNumbers;

public class Experience {
    private int maxExperience, level, experience;

    public enum Skill {
        HEALTH_REGEN("Life & Shield Regen"), HEALTH_RAW("Maximum Life & Shield"), HEALTH_LIFE("Maximum Life & Life Regen"), HEALTH_SHIELD("Maximum Shield & Shield Regen"),
        STAMINA_REGEN("Stamina & Reserve Regen"), STAMINA_RAW("Maximum Stamina & Reserve"), STAMINA_STAMINA("Maximum Stamina & Stamina Regen"), STAMINA_RESERVE("Maximum Reserve & Reserve Regen"),
        RUN_AIR_ACC("Movement Speed"), JUMP_ACC("Jump Power"), JET_ACC("Jet Speed"), BOOST_GLIDE_ACC("Boost Power");

        final String name;
        final int value;

        Skill(String name) {
            this.name = name;
            this.value = ordinal();
        }
    }

    private static final Skill[] skillValues = Skill.values();

    private int unspentPoints, points[];
    private Stats stats;

    Experience(int maxExperience, Stats stats) {
        this.maxExperience = maxExperience;
        points = new int[getSkillCount()];
        this.stats = stats;
    }

    void add(int amount) {
        experience += amount;
        if (experience >= maxExperience) {
            int levelGain = experience / maxExperience;
            experience -= levelGain * maxExperience;
            level += levelGain;
            unspentPoints += levelGain;
        }
    }

    public void spendPoint(Skill skill) {
        if (unspentPoints == 0)
            return;

        unspentPoints--;
        points[skill.value]++;

        updateStats();
    }

    public void spendPoint(Skill skill, int numPoints) {
        if (unspentPoints == 0)
            return;

        numPoints = MathNumbers.min(numPoints, unspentPoints);

        unspentPoints -= numPoints;
        points[skill.value] += numPoints;

        updateStats();
    }

    private void updateStats() {
        stats.runAcc.update(points[Skill.RUN_AIR_ACC.value]);
        stats.jumpAcc.update(points[Skill.JUMP_ACC.value]);
        stats.airAcc.update(points[Skill.RUN_AIR_ACC.value]);
        stats.jetAcc.update(points[Skill.JET_ACC.value]);
        stats.boostAcc.update(points[Skill.BOOST_GLIDE_ACC.value]);
        stats.glideAcc.update(points[Skill.BOOST_GLIDE_ACC.value]);

        stats.stamina.update(points[Skill.STAMINA_RAW.value] + points[Skill.STAMINA_STAMINA.value]);
        stats.staminaRegen.update(points[Skill.STAMINA_REGEN.value] + points[Skill.STAMINA_STAMINA.value]);
        stats.staminaReserve.update(points[Skill.STAMINA_RAW.value] + points[Skill.STAMINA_RESERVE.value]);
        stats.staminaReserveRegen.update(points[Skill.STAMINA_REGEN.value] + points[Skill.STAMINA_RESERVE.value]);
        stats.life.update(points[Skill.HEALTH_RAW.value] + points[Skill.HEALTH_LIFE.value]);
        stats.lifeRegen.update(points[Skill.HEALTH_REGEN.value] + points[Skill.HEALTH_LIFE.value]);
        stats.shield.update(points[Skill.HEALTH_RAW.value] + points[Skill.HEALTH_SHIELD.value]);
        stats.shieldRegen.update(points[Skill.HEALTH_REGEN.value] + points[Skill.HEALTH_SHIELD.value]);
    }

    int level() {
        return level;
    }

    float percent() {
        return 1f * experience / maxExperience;
    }

    public int getSkillCount() {
        return skillValues.length;
    }

    public int getUnspentPoints() {
        return unspentPoints;
    }

    public String getText(Skill skill) {
        return skill.name + " " + points[skill.value];
    }

    public Skill getSkill(int i) {
        return skillValues[i];
    }
}