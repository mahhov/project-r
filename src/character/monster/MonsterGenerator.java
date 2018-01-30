package character.monster;

import character.Human;
import character.Monster;
import character.monster.motion.MonsterMotion;
import character.monster.motion.RunawayMotion;

public class MonsterGenerator {
    public static MonsterDetails createBugDetails() {
        MonsterDetails details = new MonsterDetails();
        details.size = .5f;
        details.movement = MonsterDetails.Movement.WALK;
        details.movementSpeed = .1f;
        details.hostility = MonsterDetails.Hostility.RUNAWAY_ON_DANGER;
        details.hostilitySpeed = 1f;
        details.runawaySightDistance = 1;
        details.runawayDangerDistance = 10;
        details.armour = MonsterDetails.Armour.NONE;
        details.life = 10;
        return details;
    }

    public static MonsterMotion createMotion(Monster monster, Human human, MonsterDetails details) {
        switch (details.hostility) {
            case RUNAWAY_ON_SIGHT:
                return new RunawayMotion(monster, human, details.movementSpeed, details.hostilitySpeed, details.runawaySightDistance, 0);
            case RUNAWAY_ON_DANGER:
                return new RunawayMotion(monster, human, details.movementSpeed, details.hostilitySpeed, details.runawaySightDistance, details.runawayDangerDistance);
            default:
                throw new RuntimeException("monster hostility type not caught in MonsterGenerator.createMotion");
        }
    }
}