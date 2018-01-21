package character.gear;

import character.Crafting;

public class Property {
    public enum PropertyType {
        HEALTH_LIFE("Life", Crafting.Source.EARTH), HEALTH_LIFE_REGEN("Life Regen", Crafting.Source.FIRE), HEALTH_SHIELD("Shield", Crafting.Source.WATER), HEALTH_SHIELD_REGEN("Shield Regen", Crafting.Source.AIR),
        STAMINA_STAMINA("Stamina", Crafting.Source.EARTH), STAMINA_STAMINA_REGEN("Stamina Regen", Crafting.Source.FIRE), STAMINA_RESERVE("Stamina Reserve", Crafting.Source.WATER), STAMINA_RESERVE_REGEN("Stamina Reserve Regen", Crafting.Source.AIR),
        ATTACK_POWER("Attack Power", Crafting.Source.EARTH), ATTACK_SPEED("Attack Speed", Crafting.Source.FIRE), ACCURACY("Accuracy", Crafting.Source.WATER), RANGE("Range", Crafting.Source.AIR),
        MOVE_SPEED("Move Speed", Crafting.Source.EARTH), JUMP_SPEED("Jump Speed", Crafting.Source.FIRE), JET_SPOWER("Jet Spower", Crafting.Source.WATER), FLY_SPEED("Fly Speed", Crafting.Source.AIR);

        public final String name;
        final int value;
        final Crafting.Source source;

        PropertyType(String name, Crafting.Source source) {
            this.name = name;
            this.value = ordinal();
            this.source = source;
        }
    }

    final PropertyType propertyType;
    private int value;

    public Property(PropertyType propertyType, int value) {
        this.propertyType = propertyType;
        this.value = value;
    }

    boolean isOfType(PropertyType propertyType) {
        return this.propertyType == propertyType;
    }

    int value() {
        return value;
    }

    String getText() {
        return propertyType.name + " " + value;
    }
}