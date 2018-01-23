package character;

import character.gear.Gear;

public class Forge {
    private int metal;
    private Log log;
    private Inventory inventory;

    Forge(Log log, Inventory inventory) {
        this.log = log;
        this.inventory = inventory;
        // todo remove
        metal = 1000;
    }

    public void forge(Equipment.GearType gearType) {
        if (inventory.add(Gear.create(gearType)))
            metal -= gearType.metalCost;
        else
            log.add(String.format("Unable to forge %s, no room in inventory", gearType.name));
    }

    public boolean canForge(Equipment.GearType gearType) {
        return gearType.metalCost <= metal;
    }

    public String getText(Equipment.GearType gearType) {
        return gearType.name + " " + gearType.metalCost;
    }

    public int getMetal() {
        return metal;
    }
}