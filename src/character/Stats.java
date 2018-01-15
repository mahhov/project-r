package character;

class Stats {
    private static final float RUN_ACC = .07f, JUMP_ACC = 1f, AIR_ACC = .02f, JET_ACC = .11f;
    private static final float BOOST_ACC = .07f, GLIDE_ACC = .05f, GLIDE_DESCENT_ACC = .02f;

    private float runAcc, jumpAcc, airAcc, jetAcc;
    private float boostAcc, glideAcc, glideDescentAcc;

    Stats() {
        setRunAcc(0);
        setJumpAcc(0);
        setAirAcc(0);
        setJetAcc(0);
        setBoostAcc(0);
        setGlideAcc(0);
        setGlideDescentAcc(0);
    }

    float getRunAcc() {
        return runAcc;
    }

    float getJumpAcc() {
        return jumpAcc;
    }

    float getAirAcc() {
        return airAcc;
    }

    float getJetAcc() {
        return jetAcc;
    }

    float getBoostAcc() {
        return boostAcc;
    }

    float getGlideAcc() {
        return glideAcc;
    }

    float getGlideDescentAcc() {
        return glideDescentAcc;
    }

    void setRunAcc(int skillPoints) {
        runAcc = RUN_ACC * (1 + skillPoints * .1f);
    }

    void setJumpAcc(int skillPoints) {
        jumpAcc = JUMP_ACC * (1 + skillPoints * .1f);
    }

    void setAirAcc(int skillPoints) {
        airAcc = AIR_ACC * (1 + skillPoints * .1f);
    }

    void setJetAcc(int skillPoints) {
        jetAcc = JET_ACC * (1 + skillPoints * .1f);
    }

    void setBoostAcc(int skillPoints) {
        boostAcc = BOOST_ACC * (1 + skillPoints * .1f);
    }

    void setGlideAcc(int skillPoints) {
        glideAcc = GLIDE_ACC * (1 + skillPoints * .1f);
    }

    void setGlideDescentAcc(int skillPoints) {
        glideDescentAcc = GLIDE_DESCENT_ACC * (1 + skillPoints * .1f);
    }
}