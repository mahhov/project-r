package character.monster;

import character.Human;
import item.Coin;
import util.Distribution;

public class MonsterRewards {
    private Human human;
    public final Distribution metal, glow[], coin;
    public final int experience;

    public MonsterRewards(Human human, MonsterDetails details) {
        this.human = human;
        metal = details.metalReward;
        glow = details.glowReward;
        coin = details.coinReward;
        experience = details.experienceReward;
    }

    public void apply() {
        human.inventoryAdd(new Coin(coin.get()));
        human.experienceAdd(experience);
    }
}