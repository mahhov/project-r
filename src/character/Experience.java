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

    private static final Skill[] SKILL_VALUES = Skill.values();

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

    public void spendPoint(Skill skill, int numPoints) {
        if (unspentPoints == 0)
            return;

        numPoints = MathNumbers.min(numPoints, unspentPoints);

        unspentPoints -= numPoints;
        points[skill.value] += numPoints;

        stats.update();
    }

    int getSkillPoints(Skill skill) {
        return points[skill.value];
    }

    int level() {
        return level;
    }

    float percent() {
        return 1f * experience / maxExperience;
    }

    public int getUnspentPoints() {
        return unspentPoints;
    }

    public String getText(Skill skill) {
        return skill.name + " " + points[skill.value];
    }

    public static int getSkillCount() {
        return SKILL_VALUES.length;
    }
    
    public static Skill getSkill(int i) {
        return SKILL_VALUES[i];
    }
}