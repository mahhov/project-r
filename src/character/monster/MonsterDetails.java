package character.monster;

public class MonsterDetails {
    enum Movement {WALK, FLY, JUMP, UNDERGROUND}

    enum Hostility {RUNAWAY, RETALIATE, AGGRESSIVE}

    enum Armour {SHIELD, PLATED, NONE}

    public float size;

    public Movement movement;
    public float movementSpeed;

    public Hostility hostility;
    public float hostilityRunawaySpeed, hostilityAggressiveDistance;

    public Armour armour;
    public float life, shield, damageReduction;
}