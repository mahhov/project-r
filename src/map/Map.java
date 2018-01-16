package map;

import engine.Engine;

public class Map {
    private Engine engine;
    private String[] instanceNames;

    public Map(Engine engine) {
        this.engine = engine;
        instanceNames = new String[16];
        for (int i = 0; i < instanceNames.length; i++)
            instanceNames[i] = "instance #" + i;
    }

    public void load(int selected) {
        engine.loadMap(selected);
    }

    public String[] getTexts() {
        return instanceNames;
    }
}