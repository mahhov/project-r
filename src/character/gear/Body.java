package character.gear;

import character.Crafting;

public class Body extends Gear {
    private static final Property.PropertyType[] PRIMARY_PROPERTIES = new Property.PropertyType[4];
    private static final Property.PropertyType[] SECONDARY_PROPERTIES = new Property.PropertyType[4];

    static {
        PRIMARY_PROPERTIES[Crafting.Source.EARTH.value] = Property.PropertyType.HEALTH_LIFE;
        PRIMARY_PROPERTIES[Crafting.Source.FIRE.value] = Property.PropertyType.HEALTH_LIFE_REGEN;
        PRIMARY_PROPERTIES[Crafting.Source.WATER.value] = Property.PropertyType.HEALTH_SHIELD;
        PRIMARY_PROPERTIES[Crafting.Source.AIR.value] = Property.PropertyType.HEALTH_SHIELD_REGEN;

        SECONDARY_PROPERTIES[Crafting.Source.EARTH.value] = Property.PropertyType.HEALTH_LIFE;
        SECONDARY_PROPERTIES[Crafting.Source.FIRE.value] = Property.PropertyType.HEALTH_LIFE_REGEN;
        SECONDARY_PROPERTIES[Crafting.Source.WATER.value] = Property.PropertyType.HEALTH_SHIELD;
        SECONDARY_PROPERTIES[Crafting.Source.AIR.value] = Property.PropertyType.HEALTH_SHIELD_REGEN;
    }

    public static final int ID = 2;

    public Body() {
        super(ID, PRIMARY_PROPERTIES, SECONDARY_PROPERTIES);
    }
}