package character.monster;

import character.Human;
import character.Monster;
import character.monster.motion.MonsterMotion;
import character.monster.motion.RetaliateMotion;
import character.monster.motion.RunawayMotion;
import util.MathRandom;

public class MonsterGenerator {
    public static MonsterDetails createRandomDetails() {
        float random = MathRandom.random(0, 1f);
        if (random > 1.9)
            return createWolfDetails();
        else if (random > .8)
            return createGoatDetails();
        else if (random > .4)
            return createBugDetails();
        else
            return createBirdDetails();
    }

    public static MonsterDetails createBugDetails() {
        MonsterDetails details = new MonsterDetails();
        details.size = .5f;
        details.movement = MonsterDetails.Movement.WALK;
        details.runAcc = .07f;
        details.wanderSpeed = .1f;
        details.hostilitySpeed = 1;
        details.hostility = MonsterDetails.Hostility.RUNAWAY;
        details.hostilitySightDistance = 1;
        details.hostilityDangerDistance = 10;
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
        details.hostility = MonsterDetails.Hostility.RUNAWAY;
        details.hostilitySightDistance = 1;
        details.hostilityDangerDistance = 10;
        details.armour = MonsterDetails.Armour.NONE;
        details.life = 10;
        return details;
    }

    public static MonsterDetails createGoatDetails() {
        MonsterDetails details = new MonsterDetails();
        details.size = 1.5f;
        details.movement = MonsterDetails.Movement.WALK;
        details.runAcc = .07f;
        details.wanderSpeed = .1f;
        details.hostilitySpeed = 1;
        details.hostility = MonsterDetails.Hostility.RETALIATE;
        details.armour = MonsterDetails.Armour.NONE;
        details.life = 50;
        return details;
    }

    public static MonsterDetails createWolfDetails() {
        MonsterDetails details = new MonsterDetails();
        details.size = 2f;
        details.movement = MonsterDetails.Movement.WALK;
        details.runAcc = .07f;
        details.wanderSpeed = .1f;
        details.hostilitySpeed = 1;
        details.hostility = MonsterDetails.Hostility.AGGRESSIVE;
        details.armour = MonsterDetails.Armour.NONE;
        details.life = 50;
        return details;
    }

    public static MonsterMotion createMotion(Monster monster, Human human, MonsterDetails details) {
        MonsterMotion motion = motionHostility(details);
        motion.setBase(monster, human, details.wanderSpeed);
        if (details.movement == MonsterDetails.Movement.FLY)
            motion.setFlyHeight(100);
        motion.setHostilityParams(details.hostilitySpeed, details.hostilitySightDistance, details.hostilityDangerDistance);
        return motion;
    }

    private static MonsterMotion motionHostility(MonsterDetails details) {
        switch (details.hostility) {
            case RUNAWAY:
                return new RunawayMotion();
            case RETALIATE:
                return new RetaliateMotion();
            default:
                throw new RuntimeException("monster hostility type not caught in MonsterGenerator.createMotion");
        }
    }
}