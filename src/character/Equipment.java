package character;

import character.gear.*;

public class Equipment {
    public enum GearType {
        BODY("Body", Body.ID), HELMET("Helmet", Helmet.ID), GLOVE("Glove", Glove.ID), BOOT("Boot", Boot.ID);

        final String name;
        final int value;
        public final int gearId;

        GearType(String name, int gearId) {
            this.name = name;
            this.value = ordinal();
            this.gearId = gearId;
        }
    }

    private static final GearType[] GEAR_TYPE_VALUES = GearType.values();

    private Gear[] gears;
    private Stats stats;

    Equipment(Stats stats) {
        gears = new Gear[getGearTypeCount()];

        gears[GearType.BODY.value] = new Body();
        gears[GearType.HELMET.value] = new Helmet();
        gears[GearType.GLOVE.value] = new Glove();
        gears[GearType.BOOT.value] = new Boot();
        gears[GearType.HELMET.value].addProperty(new Property(Property.PropertyType.STAMINA_STAMINA_REGEN, 1)); // todo remove

        this.stats = stats;
    }

    int getEquipmentBonus(Property.PropertyType propertyType) {
        int sum = 0;
        for (Gear gear : gears)
            if (gear != null)
                sum += gear.getEquipmentBonus(propertyType);
        return sum;
    }

    void unequip(GearType gearType) {
        gears[gearType.value] = null;
        stats.update();
    }

    void equip(GearType gearType, Gear gear) {
        gears[gearType.value] = gear;
        stats.update();
    }

    public String getText(GearType gearType) {
        return gears[gearType.value] != null ? gears[gearType.value].getText() : "--Unequiped--";
    }

    public Gear getGear(GearType gearType) {
        return gears[gearType.value];
    }

    public static int getGearTypeCount() {
        return GEAR_TYPE_VALUES.length;
    }

    public static GearType getGearType(int i) {
        return GEAR_TYPE_VALUES[i];
    }
}