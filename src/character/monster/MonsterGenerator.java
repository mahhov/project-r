package character.monster;

public class MonsterGenerator {
    public static MonsterDetails createBugDetails() {
        MonsterDetails details = new MonsterDetails();
        details.size = .5f;
        details.movement = MonsterDetails.Movement.WALK;
        details.movementSpeed = .3f;
        details.hostility = MonsterDetails.Hostility.RUNAWAY;
        details.hostilityRunawaySpeed = 1f;
        details.armour = MonsterDetails.Armour.NONE;
        details.life = 10;
        return details;
    }
}