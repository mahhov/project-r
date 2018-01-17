package character;

import character.gear.Gear;

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

        final String name;
        final int value;
        final Source source;

        PropertyType(String name, Source source) {
            this.name = name;
            this.value = ordinal();
            this.source = source;
        }
    }

    private Gear body, helmet, glove, boot;
    private Stats stats;

    public Equipment(Stats stats) {
        this.stats = stats;
    }

    int getEquipmentBonus(PropertyType propertyType) {
        return body.getEquipmentBonus(propertyType) + helmet.getEquipmentBonus(propertyType) + glove.getEquipmentBonus(propertyType) + boot.getEquipmentBonus(propertyType);
    }
}