package character;

import item.gear.*;

public class Equipment {
    public enum GearType {
        BODY("Body", Body.ID, 400), HELMET("Helmet", Helmet.ID, 200), GLOVE("Glove", Glove.ID, 200), BOOT("Boot", Boot.ID, 200), WEAPON("Weapon", Weapon.ID, 600), MODULE("Module", Module.ID, 100);

        final String name;
        final int value;
        public final int gearId;
        final int metalCost;

        GearType(String name, int gearId, int metalCost) {
            this.name = name;
            value = ordinal();
            this.gearId = gearId;
            this.metalCost = metalCost;
        }
    }

    private static final GearType[] GEAR_TYPE_VALUES = GearType.values();
    public static final int MODULE_COUNT = 6;

    private Gear[] gears;
    private Module[] modules;
    private Stats stats;

    Equipment(Stats stats) {
        gears = new Gear[getGearTypeCount()];
        for (int i = 0; i < gears.length; i++)
            gears[i] = Gear.create(GEAR_TYPE_VALUES[i]);
        modules = new Module[MODULE_COUNT];
        this.stats = stats;
    }

    int getEquipmentBonus(Property.PropertyType propertyType) {
        int sum = 0;

        for (Gear gear : gears)
            if (gear != null)
                sum += gear.getEquipmentBonus(propertyType);

        for (Module module : modules)
            if (module != null)
                sum += module.getEquipmentBonus(propertyType);

        return sum;
    }

    public Gear getGear(GearType gearType) {
        return gears[gearType.value];
    }

    void unequip(GearType gearType) {
        gears[gearType.value] = null;
        stats.update();
    }

    void equip(GearType gearType, Gear gear) {
        gears[gearType.value] = gear;
        stats.update();
    }

    public Module getModule(int moduleIndex) {
        return modules[moduleIndex];
    }

    void unequipModule(int moduleIndex) {
        modules[moduleIndex] = null;
        stats.update();
    }

    boolean equipModule(int moduleIndex, Module module) {
        if (totalModuleWight() + module.getWeight() - modules[moduleIndex].getWeight() > Module.MAX_MODULE_WEIGHT) // todo make MAX_MODULE_WEIGHT property of stats 
            return false;

        modules[moduleIndex] = module;
        stats.update();
        return true;
    }

    private int totalModuleWight() {
        int weight = 0;
        for (Module module : modules)
            if (module != null)
                weight += module.getWeight();
        return weight;
    }

    public String getGearText(GearType gearType) {
        return gears[gearType.value] != null ? gears[gearType.value].getText() : "--Unequiped--";
    }

    public String getModuleText(int i) {
        return modules[i] != null ? modules[i].getText() : "--Unequiped--";
    }

    public static int getGearTypeCount() {
        return GEAR_TYPE_VALUES.length - 1;
    }

    public static GearType getGearType(int i) {
        return GEAR_TYPE_VALUES[i];
    }

    public static GearType[] getGearTypes() {
        return GEAR_TYPE_VALUES;
    }
}