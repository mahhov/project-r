package character;

public class Experience {
    private int maxExperience, level, experience;

    public enum Skill {
        LIFE_REGEN("Life & Shield Regen"), LIFE_RAW("Maximum Life & Shield"), LIFE("Maximum Life & Life Regen"), SHIELD("Maximum Shield & Shield Regen"),
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

        stats.runAcc.update(points[Skill.RUN_AIR_ACC.value]);
        stats.jumpAcc.update(points[Skill.JUMP_ACC.value]);
        stats.airAcc.update(points[Skill.RUN_AIR_ACC.value]);
        stats.jetAcc.update(points[Skill.JET_ACC.value]);
        stats.boostAcc.update(points[Skill.BOOST_GLIDE_ACC.value]);
        stats.glideAcc.update(points[Skill.BOOST_GLIDE_ACC.value]);
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