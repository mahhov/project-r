package character.monster;

public class MonsterDetails {
    enum Movement {WALK, FLY, JUMP, UNDERGROUND}

    enum Hostility {RUNAWAY_ON_SIGHT, RUNAWAY_ON_DANGER, RETALIATE, AGGRESSIVE}

    enum Armour {SHIELD, PLATED, NONE}

    public float size;

    public Movement movement;
    public float runAcc, airAcc, jetAcc;
    public float wanderSpeed, hostilitySpeed;

    public Hostility hostility;
    public float runawaySightDistance, runawayDangerDistance;

    public Armour armour;
    public float life, shield, damageReduction;
}