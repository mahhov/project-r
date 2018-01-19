package character;

import character.gear.*;

public class Equipment {
    public enum Source {
        EARTH("Earth"), FIRE("Fire"), WATER("Water"), AIR("Air");

        final String name;
        final int value;

        Source(String name) {
            this.name = name;
            this.value = ordinal();
        }
    }

    public enum PropertyType {
        HEALTH_LIFE("Life", Source.EARTH), HEALTH_LIFE_REGEN("Life Regen", Source.FIRE), HEALTH_SHIELD("Shield", Source.WATER), HEALTH_SHIELD_REGEN("Shield Regen", Source.AIR),
        STAMINA_STAMINA("Stamina", Source.EARTH), STAMINA_STAMINA_REGEN("Stamina Regen", Source.FIRE), STAMINA_RESERVE("Stamina Reserve", Source.WATER), STAMINA_RESERVE_REGEN("Stamina Reserve Regen", Source.AIR),
        ATTACK_POWER("Attack Power", Source.EARTH), ATTACK_SPEED("Attack Speed", Source.FIRE), ACCURACY("Accuracy", Source.WATER), RANGE("Range", Source.AIR),
        MOVE_SPEED("Move Speed", Source.EARTH), JUMP_SPEED("Jump Speed", Source.FIRE), JET_SPOWER("Jet Spower", Source.WATER), FLY_SPEED("Fly Speed", Source.AIR);

        public final String name;
        final int value;
        final Source source;

        PropertyType(String name, Source source) {
            this.name = name;
            this.value = ordinal();
            this.source = source;
        }
    }

    public enum GearType {
        BODY("Body"), HELMET("Helmet"), GLOVE("Glove"), BOOT("Boot");

        final String name;
        final int value;

        GearType(String name) {
            this.name = name;
            this.value = ordinal();
        }
    }

    private static final GearType[] GEAR_TYPE_VALUES = GearType.values();

    private Gear[] gears;
    private Stats stats; // todo update stats when equipment updated

    Equipment(Stats stats) {
        gears = new Gear[getGearTypeCount()];

        gears[GearType.BODY.value] = new Body();
        gears[GearType.HELMET.value] = new Helmet();
        gears[GearType.GLOVE.value] = new Glove();
        gears[GearType.BOOT.value] = new Boot();
        gears[GearType.HELMET.value].addProperty(new Property(PropertyType.STAMINA_STAMINA_REGEN, 1)); // todo remove

        this.stats = stats;
    }

    int getEquipmentBonus(PropertyType propertyType) {
        int sum = 0;
        for (Gear gear : gears)
            sum += gear.getEquipmentBonus(propertyType);
        return sum;
    }

    Gear getGear(GearType gearType) {
        return gears[gearType.value];
    }

    void unequip(GearType gearType) {
        gears[gearType.value] = null;
    }

    public String getText(GearType gearType) {
        return !isUnequiped(gearType) ? gearType.name + " " + gears[gearType.value].getText() : "--Unequiped--";
    }

    public String getText(GearType gearType, int property) {
        return gears[gearType.value].getText(property);
    }

    public boolean isUnequiped(GearType gearType) {
        return gears[gearType.value] == null;
    }

    public static int getGearTypeCount() {
        return GEAR_TYPE_VALUES.length;
    }

    public static GearType getGearType(int i) {
        return GEAR_TYPE_VALUES[i];
    }
}