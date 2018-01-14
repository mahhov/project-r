package character;

class Experience {
    private int maxExperience, level, experience;

    Experience(int maxExperience) {
        this.maxExperience = maxExperience;
    }

    void add(int amount) {
        experience += amount;
        if (experience >= maxExperience) {
            level++;
            experience -= maxExperience;
        }
    }

    int level() {
        return level;
    }

    float percent() {
        return 1f * experience / maxExperience;
    }
}