package character.monster;

import character.Human;
import character.Monster;
import character.monster.motion.MonsterMotion;
import character.monster.motion.RunawayMotion;
import util.MathRandom;

public class MonsterGenerator {
    public static MonsterDetails createRandomDetails() {
        if (MathRandom.random(.5))
            return createBugDetails();
        else
            return createBirdDetails();
    }

    public static MonsterDetails createBugDetails() {
        MonsterDetails details = new MonsterDetails();
        details.size = .5f;
        details.movement = MonsterDetails.Movement.WALK;
        details.runAcc = .07f;
        details.airAcc = 0;
        details.jetAcc = 0;
        details.wanderSpeed = .1f;
        details.hostilitySpeed = 1;
        details.hostility = MonsterDetails.Hostility.RUNAWAY_ON_DANGER;
        details.runawaySightDistance = 1;
        details.runawayDangerDistance = 10;
        details.armour = MonsterDetails.Armour.NONE;
        details.life = 10;
        return details;
    }

    public static MonsterDetails createBirdDetails() {
        MonsterDetails details = new MonsterDetails();
        details.size = 1;
        details.movement = MonsterDetails.Movement.FLY;
        details.runAcc = .07f;
        details.airAcc = .02f;
        details.jetAcc = .11f;
        details.wanderSpeed = .1f;
        details.hostilitySpeed = 1;
        details.hostility = MonsterDetails.Hostility.RUNAWAY_ON_DANGER;
        details.runawaySightDistance = 1;
        details.runawayDangerDistance = 10;
        details.armour = MonsterDetails.Armour.NONE;
        details.life = 10;
        return details;
    }

    public static MonsterMotion createMotion(Monster monster, Human human, MonsterDetails details) {
        switch (details.hostility) {
            case RUNAWAY_ON_SIGHT:
                return new RunawayMotion(monster, human, details.wanderSpeed, details.hostilitySpeed, details.runawaySightDistance, 0, details.movement == MonsterDetails.Movement.FLY ? 100 : 0);
            case RUNAWAY_ON_DANGER:
                return new RunawayMotion(monster, human, details.wanderSpeed, details.hostilitySpeed, details.runawaySightDistance, details.runawayDangerDistance, details.movement == MonsterDetails.Movement.FLY ? 100 : 0);
            default:
                throw new RuntimeException("monster hostility type not caught in MonsterGenerator.createMotion");
        }
    }
}