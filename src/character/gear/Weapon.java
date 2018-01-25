package character.gear;

import character.Crafting;

public class Weapon extends Gear {
    private static final Property.PropertyType[] PROPERTIES = new Property.PropertyType[4];

    static {
        PROPERTIES[Crafting.Source.EARTH.value] = Property.PropertyType.ATTACK_POWER;
        PROPERTIES[Crafting.Source.FIRE.value] = Property.PropertyType.ATTACK_SPEED;
        PROPERTIES[Crafting.Source.WATER.value] = Property.PropertyType.ACCURACY;
        PROPERTIES[Crafting.Source.AIR.value] = Property.PropertyType.RANGE;
    }

    public static final int ID = 6;

    public Weapon() {
        super(ID, PROPERTIES, PROPERTIES);
    }

    @Override
    public String getText() {
        return "WEAPON ~~~";
    }
}