package character;

public class Experience {
    private int maxExperience, level, experience;

    public enum Skill {
        LIFE_REGEN("Life & Stamina Regen"), LIFE_RAW("Maximum Life & Stamina"), LIFE("Maximum Life & Life Regen"), SHIELD("Maximum Shield & Shield Regen"),
        STAMINA_REGEN("Stamina & Reserve Regen"), STAMINA_RAW("Maximum Stamina & Reserve"), STAMINA("Maximum Stamina & Stamina Regen"), RESERVE("Maximum Reserve & Reserve Regen"),
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
            level++;
            unspentPoints++;
            experience -= maxExperience;
        }
    }

    public void spendPoint(Skill skill) {
        if (unspentPoints == 0)
            return;

        unspentPoints--;
        points[skill.value]++;

        switch (skill) {
            case RUN_AIR_ACC:
                stats.setRunAcc(points[skill.value]);
        }
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