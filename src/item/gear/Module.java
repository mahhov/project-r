package item.gear;

import character.Crafting;

public class Module extends Gear {
    public static final int MODULE_MAX_PROPERTIES = 4, MAX_MODULE_WEIGHT = 24;

    private static final Property.PropertyType[] PRIMARY_PROPERTIES = new Property.PropertyType[4];

    static {
        PRIMARY_PROPERTIES[Crafting.Source.EARTH.value] = Property.PropertyType.ATTACK_POWER;
        PRIMARY_PROPERTIES[Crafting.Source.FIRE.value] = Property.PropertyType.ATTACK_SPEED;
        PRIMARY_PROPERTIES[Crafting.Source.WATER.value] = Property.PropertyType.ACCURACY;
        PRIMARY_PROPERTIES[Crafting.Source.AIR.value] = Property.PropertyType.RANGE;
    }

    public static final int ID = 7;
    private static final int BASE_WEIGHT = 2;

    Module() {
        super(ID, PRIMARY_PROPERTIES, null);
    }

    public int getWeight() {
        return BASE_WEIGHT + getNumProperties();
    }

    @Override
    public String getText() {
        return "im a module";
    }
}