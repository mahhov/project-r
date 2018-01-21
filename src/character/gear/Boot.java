package character.gear;

import character.Crafting;

public class Boot extends Gear {
    private static final Property.PropertyType[] PRIMARY_PROPERTIES = new Property.PropertyType[] {};
    private static final Property.PropertyType[] SECONDARY_PROPERTIES = new Property.PropertyType[] {};

    static {
        PRIMARY_PROPERTIES[Crafting.Source.EARTH.value] = Property.PropertyType.HEALTH_LIFE;
        PRIMARY_PROPERTIES[Crafting.Source.FIRE.value] = Property.PropertyType.HEALTH_LIFE_REGEN;
        PRIMARY_PROPERTIES[Crafting.Source.WATER.value] = Property.PropertyType.HEALTH_SHIELD;
        PRIMARY_PROPERTIES[Crafting.Source.AIR.value] = Property.PropertyType.HEALTH_SHIELD_REGEN;

        SECONDARY_PROPERTIES[Crafting.Source.EARTH.value] = Property.PropertyType.MOVE_SPEED;
        SECONDARY_PROPERTIES[Crafting.Source.FIRE.value] = Property.PropertyType.JUMP_SPEED;
        SECONDARY_PROPERTIES[Crafting.Source.WATER.value] = Property.PropertyType.JET_SPEED;
        SECONDARY_PROPERTIES[Crafting.Source.AIR.value] = Property.PropertyType.FLY_SPEED;
    }

    public static final int ID = 5;

    public Boot() {
        super(ID, PRIMARY_PROPERTIES, SECONDARY_PROPERTIES);
    }
}