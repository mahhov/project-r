package map;

public class Map {
    private String[] instanceNames;

    public Map() {
        instanceNames = new String[16];
        for (int i = 0; i < instanceNames.length; i++)
            instanceNames[i] = "instance #" + i;
    }

    public void load(int selected) {
        System.out.println("load map " + selected);
    }

    public String[] getTexts() {
        return instanceNames;
    }
}