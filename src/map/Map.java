package map;

import ui.TextSystem;
import util.LList;

public class Map implements TextSystem {
    private LList<String> instanceNames;

    public Map() {
        instanceNames = new LList<>();
        for (int i = 0; i < 16; i++)
            instanceNames.addTail("instance #" + i);
    }

    @Override
    public Iterable<String> getTexts() {
        return instanceNames;
    }
}