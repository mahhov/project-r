package character;

class Stats {
    final Stat runAcc, jumpAcc, airAcc, jetAcc;
    final Stat boostAcc, glideAcc, glideDescentAcc;

    Stats() {
        runAcc = new Stat(.07f, .1f);
        jumpAcc = new Stat(1f, .1f);
        airAcc = new Stat(.02f, .1f);
        jetAcc = new Stat(.11f, .1f);
        boostAcc = new Stat(.07f, .1f);
        glideAcc = new Stat(.05f, .1f);
        glideDescentAcc = new Stat(.02f, .1f);
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