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

    Experience(int maxExperience) {
        this.maxExperience = maxExperience;
        points = new int[getSkillCount()];
    }

    void add(int amount) {
        experience += amount;
        if (experience >= maxExperience) {
            level++;
            unspentPoints++;
            experience -= maxExperience;
        }
    }

    public void spendPoint(int i) {
        if (unspentPoints == 0)
            return;

        unspentPoints--;
        points[i]++;
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

    public String getText(int i) {
        return skillValues[i].name + " " + points[i];
    }
}