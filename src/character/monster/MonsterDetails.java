package character.monster;

public class MonsterDetails {
    enum Movement {WALK, FLY, JUMP, UNDERGROUND}

    enum Hostility {RUNAWAY_ON_SIGHT, RUNAWAY, RETALIATE, AGGRESSIVE}

    enum Armour {SHIELD, PLATED, NONE}

    public float size;

    public Movement movement;
    public float movementSpeed;

    public Hostility hostility;
    public float hostilitySpeed, hostilityDistance;

    public Armour armour;
    public float life, shield, damageReduction;
}