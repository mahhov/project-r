package character.monster;

import character.Human;
import character.Monster;
import character.monster.attack.DegenAttack;
import character.monster.attack.MonsterAttack;
import character.monster.attack.NoneAttack;
import character.monster.motion.MonsterMotion;
import character.monster.motion.RetaliateMotion;
import character.monster.motion.RunawayMotion;
import util.Distribution;
import util.MathRandom;

public class MonsterGenerator {
    public static MonsterDetails createRandomDetails() {
        float random = MathRandom.random(0, 1f);
        if (random > .8)
            return createGoatDetails();
        else if (random > .4)
            return createBugDetails();
        else
            return createBirdDetails();
    }

    public static MonsterDetails createBugDetails() {
        MonsterDetails details = new MonsterDetails();
        details.color = new float[] {0, 1, 1, 1};
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
        details.attack = MonsterDetails.Attack.NONE;
        details.metalReward = new Distribution(0, 0);
        details.glowReward = new Distribution[] {};
        details.coinReward = new Distribution(10, 20);
        details.experienceReward = 10;
        return details;
    }

    public static MonsterDetails createBirdDetails() {
        MonsterDetails details = new MonsterDetails();
        details.color = new float[] {0, 1, .5f, 1};
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
        details.attack = MonsterDetails.Attack.NONE;
        details.metalReward = new Distribution(0, 0);
        details.glowReward = new Distribution[] {};
        details.coinReward = new Distribution(15, 25);
        details.experienceReward = 10;
        return details;
    }

    public static MonsterDetails createGoatDetails() {
        MonsterDetails details = new MonsterDetails();
        details.color = new float[] {1, 1, 0, 1};
        details.size = 1.5f;
        details.movement = MonsterDetails.Movement.WALK;
        details.runAcc = .07f;
        details.wanderSpeed = .1f;
        details.hostilitySpeed = 1;
        details.hostility = MonsterDetails.Hostility.RETALIATE;
        details.armour = MonsterDetails.Armour.NONE;
        details.life = 50;
        details.attack = MonsterDetails.Attack.DEGEN;
        details.attackDamage = 1;
        details.attackAoe = 10;
        details.metalReward = new Distribution(0, 0);
        details.glowReward = new Distribution[] {};
        details.coinReward = new Distribution(20, 30);
        details.experienceReward = 50;
        return details;
    }

    public static MonsterAttack createAttack(Monster monster, Human human, MonsterDetails details) {
        MonsterAttack attack = baseAttack(details);
        attack.setBase(monster, human);
        attack.setParams(details.attackSpeed, details.attackDamage, details.attackRange, details.attackSize, details.attackAoe);
        return attack;
    }

    private static MonsterAttack baseAttack(MonsterDetails details) {
        switch (details.attack) {
            case NONE:
                return new NoneAttack();
            case DEGEN:
                return new DegenAttack();
            default:
                throw new RuntimeException("monster attack type not caught in MonsterGenerator.baseAttack");
        }
    }

    public static MonsterMotion createMotion(Monster monster, Human human, MonsterDetails details) {
        MonsterMotion motion = baseMotion(details);
        motion.setBase(monster, human, details.wanderSpeed);
        if (details.movement == MonsterDetails.Movement.FLY)
            motion.setFlyHeight(100);
        motion.setParams(details.hostilitySpeed, details.hostilitySightDistance, details.hostilityDangerDistance);
        return motion;
    }

    private static MonsterMotion baseMotion(MonsterDetails details) {
        switch (details.hostility) {
            case RUNAWAY:
                return new RunawayMotion();
            case RETALIATE:
                return new RetaliateMotion();
            default:
                throw new RuntimeException("monster hostility type not caught in MonsterGenerator.baseMotion");
        }
    }
}