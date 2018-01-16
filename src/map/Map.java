package map;

import engine.Game;

public class Map {
    private Game game;
    private String[] instanceNames;

    public Map(Game game) {
        this.game = game;
        instanceNames = new String[16];
        for (int i = 0; i < instanceNames.length; i++)
            instanceNames[i] = "instance #" + i;
    }

    public void load(int selected) {
        game.loadMap(selected);
    }

    public String[] getTexts() {
        return instanceNames;
    }
}