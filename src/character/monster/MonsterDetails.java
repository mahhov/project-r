package character.monster;

public class MonsterDetails {
    enum Movement {WALK, FLY, JUMP, UNDERGROUND}

    enum Hostility {RUNAWAY, RETALIATE, AGGRESSIVE}

    enum Armour {SHIELD, PLATED, NONE}

    public float size;

    public Movement movement;
    public float runAcc, airAcc, jetAcc;
    public float wanderSpeed;

    public Hostility hostility;
    public float hostilitySpeed, hostilitySightDistance, hostilityDangerDistance;

    public Armour armour;
    public float life, shield, damageReduction;
}